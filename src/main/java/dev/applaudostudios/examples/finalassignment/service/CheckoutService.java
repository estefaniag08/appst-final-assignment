package dev.applaudostudios.examples.finalassignment.service;

import dev.applaudostudios.examples.finalassignment.common.dto.*;
import dev.applaudostudios.examples.finalassignment.common.exception.service.CheckoutServiceException;
import dev.applaudostudios.examples.finalassignment.common.exception.service.ForbbidenOrderAccess;
import dev.applaudostudios.examples.finalassignment.common.exception.service.OrderAlreadyChecked;
import dev.applaudostudios.examples.finalassignment.model.orders.OrderFacade;
import dev.applaudostudios.examples.finalassignment.persistence.query.OrderQueries;
import dev.applaudostudios.examples.finalassignment.persistence.query.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CheckoutService {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserQueries userQueries;
    @Autowired
    private OrderQueries orderQueries;
    @Autowired
    private OrderFacade orderFacade;

    //public CheckoutService(ProductService productService) {
    //    this.productService = productService;
    //}

    public OrderDto createOrder(String userEmailAddress, OrderDto orderDto) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = orderFacade.initializeOrder(updateStockFromItemList(orderDto.getOrderItems()));

        order.setUser(user);
        orderFacade.reserveOrder(order);
        if (orderDto.getAddress() != null) {
            AddressDto address = userQueries.getAddressFromUser(user, orderDto.getAddress().getId());
            order.setAddress(address);
            order = orderFacade.generateDispatchCode(order);
        }
        if (orderDto.getPaymentMethod() != null) {
            PaymentDto paymentDto = userQueries.getPaymentFromUser(user, orderDto.getPaymentMethod().getId());
            order.setPaymentMethod(paymentDto);
            order = orderFacade.verifyOrder(order);
        }
        return orderQueries.createOrder(order);

    }

    public OrderDto saveAddress(String userEmailAddress, Long orderId, AddressDto addressDto) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = findOrderById(orderId);
        AddressDto address = userQueries.getAddressFromUser(user, addressDto.getId());
        if(orderIsOfUser(order, user.getEmail()) && address!= null){
            order.setAddress(address);
            order = orderFacade.generateDispatchCode(order);
        }
        return orderQueries.mapToDto(orderQueries.updateOrder(orderId, order));
    }

    public OrderDto savePaymentMethod(String userEmailAddress, Long orderId, PaymentDto paymentMethod) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = findOrderById(orderId);
        if(orderIsOfUser(order, user.getEmail())){
            PaymentDto paymentDto = userQueries.getPaymentFromUser(user, paymentMethod.getId());
            order.setPaymentMethod(paymentDto);
            order = orderFacade.verifyOrder(order);
        }
        return orderQueries.mapToDto(orderQueries.updateOrder(orderId, order));
    }

    public OrderDto updateOrderItemStock(String userEmailAddress, Long orderId, ItemDto itemDto) {
        OrderDto order = findOrderById(orderId);
        if(orderIsOfUser(order, userEmailAddress) && orderNotChecked(order)){
            Optional<ItemDto> currentItem = order.getOrderItems().stream()
                    .filter(item -> Objects.equals(item.getCode(), itemDto.getCode())
                    ).findFirst();
            if (currentItem.isPresent()) {
                if (itemDto.getUnits() > 0) {
                    Optional<ProductDto> product = productService.get(itemDto.getCode());
                    if (product.isPresent()) {
                        productService.returnStock(product.get().getId(), currentItem.get().getUnits());
                        productService.reserveStock(product.get().getId(), itemDto.getUnits());
                        itemDto.setUnitPrice(product.get().getPricePerUnit());
                    }
                    orderFacade.saveItemToOrder(order, itemDto);
                    orderQueries.mapToDto(orderQueries.updateOrderItem(orderId, itemDto));
                    return orderQueries.mapToDto(orderQueries.updateOrder(orderId, order));
                } else {
                    return deleteOrderItem(userEmailAddress, orderId, itemDto.getId());
                }
            } else {
                throw new CheckoutServiceException("Order item with code " + itemDto.getCode() + "does not exists");
            }
        }
        return null;
    }

    public OrderDto addOrderItem(String userEmailAddress, Long orderId, ItemDto itemDto) {
        OrderDto order = findOrderById(orderId);
        if(orderIsOfUser(order, userEmailAddress) && orderNotChecked(order)){
            List<ItemDto> itemList = new ArrayList<>();
            itemList.add(itemDto);
            Optional<ItemDto> currentItem = order.getOrderItems().stream()
                    .filter(itemCurrent -> Objects.equals(itemCurrent.getCode(), itemDto.getCode())
                    ).findFirst();
            updateStockFromItemList(itemList);
            if (currentItem.isEmpty()) {
                orderFacade.saveItemToOrder(order, itemList.get(0));
                orderQueries.addOrderItem(order, itemList.get(0));
                return orderQueries.mapToDto(orderQueries.updateOrder(orderId, order));
            } else {
                throw new CheckoutServiceException("The order item with the given id already exists.");
            }
        }
        return null;
    }

    public OrderDto deleteOrderItem(String userEmailAddress,Long orderId, Long itemId) {
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
        if(orderIsOfUser(order, userEmailAddress) && orderNotChecked(order)){
            Optional<ItemDto> currentItem = order.getOrderItems().stream()
                    .filter(item -> Objects.equals(item.getCode(), itemId)
                    ).findFirst();
            if (currentItem.isPresent()) {
                Optional<ProductDto> product = productService.get(currentItem.get().getCode());
                if (product.isPresent()) {
                    productService.returnStock(product.get().getId(), currentItem.get().getUnits());
                }
                orderFacade.deleteItemFromOrder(order, currentItem.get());
                order = orderQueries.deleteOrderItem(orderId,itemId);
                orderQueries.updateOrder(orderId, order);
                if(order.getOrderItems().isEmpty()){
                    deleteOrder(orderId);
                    return null;
                } else {
                    return order;
                }
            } else {
                throw new CheckoutServiceException("Order item with code " + itemId + "does not exists");
            }
        }
        return null;
    }

    public void deleteOrder(Long orderId){

        OrderDto order = findOrderById(orderId);
        if(order != null){
            for(ItemDto item: order.getOrderItems()
                 ) {
                Optional<ProductDto> product = productService.get(item.getCode());
                if(product.isPresent()){
                    productService.returnStock(product.get().getId(), item.getUnits());
                }
            }
            orderQueries.deleteOrder(orderId);
        }
    }

    private List<ItemDto> updateStockFromItemList(List<ItemDto> listOfItems) {
        for (ItemDto item : listOfItems) {
            Optional<ProductDto> product = productService.get(item.getCode());
            if (product.isPresent()) {
                item.setName(product.get().getProductName());
                item.setUnitPrice(product.get().getPricePerUnit());
                if (!productService.reserveStock(product.get().getId(), item.getUnits())) {
                    listOfItems.remove(item);
                }
            }
        }
        return listOfItems;
    }

    public OrderDto findOrderById(Long orderId){
        return orderQueries.mapToDto(orderQueries.getOrderById(orderId));
    }

    private boolean orderIsOfUser(OrderDto order, String userEmail){
        if(Objects.equals(order.getUser().getEmail(), userEmail)){
            return true;
        } else {
            throw new ForbbidenOrderAccess("The order does not belong to user.");
        }

    }

    private boolean orderNotChecked(OrderDto order){
        if(order.getAddress() != null || order.getPaymentMethod() != null){
            throw new OrderAlreadyChecked("The order has already been checked out. The items can't be modified.");
        } else {
          return true;
        }
    }
}
