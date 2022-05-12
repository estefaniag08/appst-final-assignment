package dev.applaudostudios.examples.finalassignment.config;

import dev.applaudostudios.examples.finalassignment.model.orders.OrderFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    //@Autowired
    //ProductService productService(ProductRepository productRepository){
    //    return new ProductService(productRepository);
    //}

   // @Autowired
   // CheckoutService checkoutService(ProductService productService){
   //     return new CheckoutService(productService);
    //}

    @Bean
    OrderFacade orderFacade(){
        return new OrderFacade();
    }
}
