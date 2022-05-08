package dev.applaudostudios.examples.finalassignment.web.controller;

import dev.applaudostudios.examples.finalassignment.common.dto.CheckoutStatusDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.service.CheckoutService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CheckoutService checkoutService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(Authentication authentication){
        System.out.println("Entra o no en get order");
        return null;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<OrderDto> createOrder(@RequestBody final OrderDto orderDto, final BindingResult bindingResult){
        checkoutService.initCheckout(1L, orderDto);

        return new ResponseEntity(orderDto, HttpStatus.CREATED);
    }

    @RequestMapping( path = "{idOrder}/address", method = RequestMethod.POST)
    public ResponseEntity<CheckoutStatusDto> addAddressToOrder(){
        return null;
    }

    @RequestMapping( path = "{idOrder}/address", method = RequestMethod.PUT)
    public ResponseEntity<CheckoutStatusDto> changeAddressOfOrder(){
        return null;
    }

    @RequestMapping( path = "{idOrder}/payment", method = RequestMethod.POST)
    public ResponseEntity<CheckoutStatusDto> addPaymentToOrder(){
        return null;
    }

    @RequestMapping( path = "{idOrder}/payment", method = RequestMethod.PUT)
    public ResponseEntity<CheckoutStatusDto> changePaymentOfOrder(){
        return null;
    }

    @RequestMapping( path = "{idOrder}/item", method = RequestMethod.POST)
    public ResponseEntity<CheckoutStatusDto> addItemListToOrder(){
        return null;
    }

    @RequestMapping( path = "{idOrder}/item", params = "idItem", method = RequestMethod.PATCH)
    public ResponseEntity<CheckoutStatusDto> modifyItemOfOrder(){
        return null;
    }

    private String getUserEmailFromAuthentication(Authentication authentication){
        KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        return kp.getKeycloakSecurityContext().getToken().getEmail();
    }

}
