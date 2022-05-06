package dev.appladostudios.examples.finalassignment.service;

import dev.appladostudios.examples.finalassignment.model.payment.PaymentMethod;
import dev.appladostudios.examples.finalassignment.persistence.model.Address;
import dev.appladostudios.examples.finalassignment.persistence.model.Order;
import dev.appladostudios.examples.finalassignment.persistence.model.User;
import dev.appladostudios.examples.finalassignment.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    ProductRepository productRepository;
    User user;
    Address userAddress;
    PaymentMethod paymentMethod;
    Order userOrder;
    public void createOrder(){

    }

    public void updateOrder(){

    }

    public void deleteOrder(){
        
    }
    public void getOrderState(){

    }
}
