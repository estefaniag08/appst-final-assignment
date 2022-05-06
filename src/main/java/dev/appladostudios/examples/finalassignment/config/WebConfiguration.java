package dev.appladostudios.examples.finalassignment.config;

import dev.appladostudios.examples.finalassignment.persistence.repository.ProductRepository;
import dev.appladostudios.examples.finalassignment.service.CheckoutService;
import dev.appladostudios.examples.finalassignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Autowired
    ProductService productService(ProductRepository productRepository){
        return new ProductService(productRepository);
    }

    @Autowired
    CheckoutService checkoutService(ProductService productService){
        return new CheckoutService(productService);
    }
    //@Bean
    //ObjectMapper objectMapper(){
    //    return new ObjectMapper();
    //}
}
