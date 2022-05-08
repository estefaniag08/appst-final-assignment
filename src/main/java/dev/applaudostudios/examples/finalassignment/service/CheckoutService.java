package dev.applaudostudios.examples.finalassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.CheckoutStatusDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.model.orders.SimpleOrder;
import dev.applaudostudios.examples.finalassignment.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CheckoutService implements Mappable<Order, OrderDto> {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ObjectMapper objectMapper;
    private final ProductService productService;

    public CheckoutService(ProductService productService) {
        this.productService = productService;
    }


    public OrderDto mapToDto(Order entity) {
        return objectMapper.convertValue(entity, OrderDto.class);
    }


    public Order mapToEntity(OrderDto entityDto) {
        return objectMapper.convertValue(entityDto, Order.class);
    }

    public boolean initCheckout(String userEmail, OrderDto orderDto){
        User user = entityManager.find(User.class, userEmail);
        Order orderEntity;
        List<OrderDetail> orderDetails = new ArrayList<>();

        orderEntity = mapToEntity(orderDto);
        orderEntity.setUser(user);
        entityManager.persist(orderEntity);

        Order finalOrderEntity = orderEntity;
        orderDto.getOrderItems().forEach(item -> {
           Product product = entityManager.find(Product.class, item.getCode());
           if(product != null){
            item.setName(product.getProductName());
            item.setUnitPrice(product.getPricePerUnit());

            product.setStock(product.getStock()-item.getUnits());
            productService.updateStock(productService.mapToDto(product));

            orderDetails.add(new OrderDetail(0, finalOrderEntity, product, item.getUnits(), item.getUnits()*item.getUnitPrice()));
           }
        });

        SimpleOrder orderModel = new SimpleOrder(orderDto.getOrderItems());
        if(orderModel.reserveOrder()){
            orderEntity = mapToEntity(orderDto);
            orderEntity.setOrderItems(orderDetails);
            orderEntity.setOrderTotal(orderModel.getOrderList().getTotal());
            orderEntity.setReservationCode(orderModel.getReservationCode());
            for (OrderDetail orderDetail : orderDetails) {
                entityManager.persist(orderDetail);
            }
            entityManager.merge(orderEntity);
            return true;
        }
        System.out.println(orderDto);
        return false;
    }
    public CheckoutStatusDto saveOrderAddress(Long idOrder, Long idUser, int idAddress){
        User user = entityManager.find(User.class, idUser);
        Address address = entityManager.find(Address.class, idAddress);
        OrderDto orderDto = mapToDto(entityManager.find(Order.class, idOrder));
        SimpleOrder orderModel = new SimpleOrder(orderDto.getOrderItems());

        orderModel.setReservationCode(orderDto.getReservationCode());
        orderModel.setVerificationCode(orderDto.getVerificationCode());
        orderModel.setOrderCode(orderDto.getOrderCode());

        if(address != null && Objects.equals(user.getId(), address.getUser().getId())){
            Order order = entityManager.find(Order.class, idOrder);
            order.setAddress(address);
            entityManager.merge(order);

        }
        return null;
    }

    public CheckoutStatusDto saveOrderPaymentMethod(Long idOrder, Long idUser, int idPaymentMethodId){
        User user = entityManager.find(User.class, idUser);
        PaymentMethod payment = entityManager.find(PaymentMethod.class, idPaymentMethodId);
        OrderDto orderDto = mapToDto(entityManager.find(Order.class, idOrder));
        SimpleOrder orderModel = new SimpleOrder(orderDto.getOrderItems());
        if(payment != null && Objects.equals(user.getId(), payment.getUser().getId())){
            Order order = entityManager.find(Order.class, idOrder);
            order.setPaymentMethod(payment);
            entityManager.merge(order);
            if(orderModel.generateOrder()) {

            } else {

            }
        }
        return null;
    }

    public CheckoutStatusDto getOrderState(Long orderId){
        return null;
    }

}
