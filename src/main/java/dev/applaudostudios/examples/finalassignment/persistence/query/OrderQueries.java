package dev.applaudostudios.examples.finalassignment.persistence.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.exception.order.OrderItemNotFoundException;
import dev.applaudostudios.examples.finalassignment.common.exception.order.OrderNotFoundException;
import dev.applaudostudios.examples.finalassignment.common.exception.order.OrderRelatedException;
import dev.applaudostudios.examples.finalassignment.persistence.model.Order;
import dev.applaudostudios.examples.finalassignment.persistence.model.OrderDetail;
import dev.applaudostudios.examples.finalassignment.persistence.model.Product;
import dev.applaudostudios.examples.finalassignment.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class OrderQueries implements Mappable<Order, OrderDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    public Order getOrderById(Long id) {
        try {
            Order foundOrder = entityManager.find(Order.class, id);
            if(foundOrder != null){
                return foundOrder;
            } else {
                throw new OrderNotFoundException("Order not found with the given id " + id);
            }

        } catch (IllegalArgumentException exception) {
            throw new OrderRelatedException("Error getting the order method from the database.");
        }
    }

    public OrderDto createOrder(OrderDto order) {
        try {
            Order orderEntity = mapToEntity(order);
            orderEntity.setId(null);
            entityManager.persist(orderEntity);
            order.getOrderItems().forEach(item -> {
                Optional<Product> product = productRepository.findById(item.getCode());
                if(product.isPresent()){
                    entityManager.persist(new OrderDetail(0, orderEntity,
                            product.get(), item.getUnits(), item.getUnits() * item.getUnitPrice()));
                }
            });
            order.setId(orderEntity.getId());
            return order;
        } catch (PersistenceException exception) {
            throw new OrderRelatedException("Error saving the order to the database.");
        }
    }

    public boolean deleteOrder(Long id) {
        try {
            Order order = getOrderById(id);
            if (order != null) {
                entityManager.remove(order);
            }
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException exception) {
            throw new OrderRelatedException("Error deleting the order from the database.");
        }
    }

    public Order updateOrder(Long id, Order order) {
        try {
            return entityManager.merge(order);
        } catch (PersistenceException exception) {
            throw new OrderRelatedException("Error updating the order to the database.");
        }
    }

    public Order addOrderItem(Long id, ItemDto item){
        try{
            Order order = getOrderById(id);
            Optional<Product> product = productRepository.findById(item.getCode());
            if(product.isPresent()){
                entityManager.persist(new OrderDetail(0, order,
                        product.get(), item.getUnits(), item.getUnits() * item.getUnitPrice()));
            }
            return order;
        }catch(PersistenceException exception){
            throw new OrderRelatedException("Error adding order item to the database.");
        }
    }
    public Order updateOrderItem(Long id, ItemDto itemDto){
        try{
            Order order = getOrderById(id);
            Optional<OrderDetail> currentItem = order.getOrderItems().stream()
                    .filter(item -> Objects.equals(item.getProduct().getId(), itemDto.getCode())).findFirst();
            if(currentItem.isPresent()){
                currentItem.get().setProductQuantity(itemDto.getUnits());
                if(currentItem.get().getProductQuantity() <=0 ){
                    deleteOrderItem(id, currentItem.get().getProduct().getId());
                    return order;
                }
                currentItem.get().setSubTotal(itemDto.getUnits()* itemDto.getUnitPrice());
                entityManager.merge(currentItem.get());
            } else {
                throw new OrderItemNotFoundException("Item with the following code"
                        + itemDto.getCode() + " not found for the order.");
            }
            return order;
        }catch(PersistenceException exception){
            throw new OrderRelatedException("Error updating the order item into the database.");
        }
    }

    public OrderDto deleteOrderItem(Long id, Long codeId){
        try{
            Order order = getOrderById(id);
            Optional<OrderDetail> currentItem = order.getOrderItems().stream()
                    .filter(item -> item.getId() == codeId).findFirst();
            if(currentItem.isPresent()){
                List <OrderDetail> orderDetailsList = order.getOrderItems();
                orderDetailsList.remove(currentItem.get());
                order.setOrderItems(orderDetailsList);

                order = entityManager.merge(order);
                //entityManager.remove(currentItem.get());
                //entityManager.detach(currentItem.get());
            }
            return mapToDto(order);

        }catch(PersistenceException exception){
            throw new OrderRelatedException("Error deleting the order item from the database.");
        }
    }
    public OrderDto mapToDto(Order entity) {
        List<ItemDto> listOfItems = new ArrayList<>();
        for(OrderDetail detail: entity.getOrderItems()){
            listOfItems.add(new ItemDto(detail.getProduct().getId(),
                    detail.getProduct().getProductName(),
                    detail.getProductQuantity(),
                    detail.getProduct().getPricePerUnit()));
        }
        OrderDto order = objectMapper.convertValue(entity, OrderDto.class);
        order.setOrderItems(listOfItems);
        return order;
    }

    public Order mapToEntity(OrderDto entityDto) {
        return objectMapper.convertValue(entityDto, Order.class);
    }
}
