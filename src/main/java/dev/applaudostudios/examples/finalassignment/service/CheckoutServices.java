package dev.applaudostudios.examples.finalassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.persistence.model.Order;
import dev.applaudostudios.examples.finalassignment.persistence.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CheckoutServices implements Mappable<Order, OrderDto> {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ObjectMapper objectMapper;
    private final ProductService productService;

    public CheckoutServices(ProductService productService) {
        this.productService = productService;
    }

    public OrderDto createOrder(String userEmailAddress, OrderDto orderDto){
        User user = getCheckoutUser(userEmailAddress);
        return null;
    }

    public OrderDto mapToDto(Order entity) {
        return objectMapper.convertValue(entity, OrderDto.class);
    }

    public Order mapToEntity(OrderDto entityDto) {
        return objectMapper.convertValue(entityDto, Order.class);
    }


    private User getCheckoutUser(String emailAddress){
        entityManager.getTransaction().begin();
        Session session = entityManager.unwrap(Session.class);

        return session.bySimpleNaturalId(User.class).load(emailAddress);
    }
}
