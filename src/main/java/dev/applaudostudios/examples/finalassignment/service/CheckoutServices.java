package dev.applaudostudios.examples.finalassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.*;
import dev.applaudostudios.examples.finalassignment.model.orders.OrderFacade;
import dev.applaudostudios.examples.finalassignment.persistence.model.Order;
import dev.applaudostudios.examples.finalassignment.persistence.query.OrderQueries;
import dev.applaudostudios.examples.finalassignment.persistence.query.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CheckoutServices implements Mappable<Order, OrderDto> {

    @Autowired
    private ObjectMapper objectMapper;
    private final ProductService productService;

    @Autowired
    private UserQueries userQueries;
    @Autowired
    private OrderQueries orderQueries;
    @Autowired
    private OrderFacade orderFacade;

    public CheckoutServices(ProductService productService) {
        this.productService = productService;
    }

    public OrderDto createOrder(String userEmailAddress, OrderDto orderDto) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = orderFacade.initializeOrder(updateStockFromItemList(orderDto.getOrderItems()));

        order.setUser(user);
        orderFacade.reserveOrder(order);
        if (orderDto.getAddress() != null) {
            AddressDto address = userQueries.getAddressFromUserId(user.getId(), orderDto.getAddress().getId());
            order.setAddress(address);
            order = orderFacade.generateDispatchCode(order);
        }
        if (orderDto.getPaymentMethod() != null) {
            PaymentDto paymentDto = userQueries.getPaymentFromUserId(user.getId(), orderDto.getPaymentMethod().getId());
            order.setPaymentMethod(paymentDto);
            order = orderFacade.verifyOrder(order);
        }
        return orderQueries.createOrder(order);

    }

    public OrderDto saveAddress(String userEmailAddress, Long orderId, AddressDto addressDto) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
        for (ItemDto item : order.getOrderItems()
        ) {
            System.out.println(item.toString());
        }
        AddressDto address = userQueries.getAddressFromUserId(user.getId(), addressDto.getId());

        order.setAddress(address);
        order = orderFacade.generateDispatchCode(order);
        return orderQueries.mapToDto(orderQueries.updateOrder(orderId, mapToEntity(order)));
    }

    public OrderDto savePaymentMethod(String userEmailAddress, Long orderId, PaymentDto paymentMethod) {
        UserDto user = userQueries.getUserFromEmail(userEmailAddress);
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
        PaymentDto paymentDto = userQueries.getPaymentFromUserId(user.getId(), paymentMethod.getId());
        order.setPaymentMethod(paymentDto);
        order = orderFacade.verifyOrder(order);
        return orderQueries.mapToDto(orderQueries.updateOrder(orderId, orderQueries.mapToEntity(order)));
    }

    public OrderDto updateOrderItemStock(Long orderId, ItemDto itemDto) {
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
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
                return orderQueries.mapToDto(orderQueries.updateOrder(orderId, mapToEntity(order)));
            } else {
                deleteOrderItem(orderId, itemDto.getId());
                return null;
            }
        } else {
            return null;
        }
    }

    public OrderDto addOrderItem(Long orderId, ItemDto itemDto) {
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
        List<ItemDto> itemList = new ArrayList<>();
        itemList.add(itemDto);

        Optional<ItemDto> currentItem = order.getOrderItems().stream()
                .filter(itemCurrent -> Objects.equals(itemCurrent.getCode(), itemDto.getCode())
                ).findFirst();

        updateStockFromItemList(itemList);

        if (currentItem.isEmpty()) {
            orderFacade.saveItemToOrder(order, itemList.get(0));
            orderQueries.mapToDto(orderQueries.addOrderItem(orderId, itemList.get(0)));
            return orderQueries.mapToDto(orderQueries.updateOrder(orderId, mapToEntity(order)));
        } else {

            return null;
        }
    }

    public OrderDto deleteOrderItem(Long orderId, Long itemId) {
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
        Optional<ItemDto> currentItem = order.getOrderItems().stream()
                .filter(item -> Objects.equals(item.getCode(), itemId)
                ).findFirst();
        if (currentItem.isPresent()) {
            Optional<ProductDto> product = productService.get(currentItem.get().getCode());
            if (product.isPresent()) {
                productService.returnStock(product.get().getId(), currentItem.get().getUnits());
            }
            //orderFacade.deleteItemFromOrder(order, currentItem.get());
            order = orderQueries.deleteOrderItem(orderId,itemId);
            //orderQueries.updateOrder(orderId, mapToEntity(order));
            if(order.getOrderItems().isEmpty()){
                deleteOrder(orderId);
                return null;
            } else {
                return order;
            }
        } else {
            return null;
        }
    }


    public void deleteOrder(Long orderId){
        OrderDto order = orderQueries.mapToDto(orderQueries.getOrderById(orderId));
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
    public OrderDto mapToDto(Order entity) {
        return objectMapper.convertValue(entity, OrderDto.class);
    }

    public Order mapToEntity(OrderDto entityDto) {
        return objectMapper.convertValue(entityDto, Order.class);
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
}
