package dev.applaudostudios.examples.finalassignment.web.controller;

import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.dto.PaymentDto;
import dev.applaudostudios.examples.finalassignment.service.CheckoutServices;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    @Autowired
    CheckoutServices checkoutServices;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(Authentication authentication){
        System.out.println("Entra o no en get order");
        return null;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<OrderDto> createOrder(@RequestBody final OrderDto orderDto,
                                                final BindingResult bindingResult,
                                                final Authentication authentication){
        OrderDto finalOrder = checkoutServices.createOrder(getUserEmailFromAuthentication(authentication), orderDto);

        return new ResponseEntity<>(finalOrder, HttpStatus.CREATED);
    }

    @RequestMapping( path = "{idOrder}/address", method = RequestMethod.PUT)
    public ResponseEntity<OrderDto> saveAddressOfOrder(@RequestBody final AddressDto addressDto,
                                                        @PathVariable("idOrder") final Long idOrder,
                                                        final BindingResult bindingResult,
                                                        final Authentication authentication){
        String userEmail = getUserEmailFromAuthentication(authentication);
        if(!userEmail.isEmpty()){
            return new ResponseEntity<>(checkoutServices.saveAddress(userEmail,idOrder,addressDto), HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping( path = "{idOrder}/payment", method = RequestMethod.PUT)
    public ResponseEntity<OrderDto> savePaymentOfOrder(@RequestBody final PaymentDto paymentDto,
                                                       @PathVariable("idOrder") final Long idOrder,
                                                       final BindingResult bindingResult,
                                                       final Authentication authentication){
        String userEmail = getUserEmailFromAuthentication(authentication);
        if(!userEmail.isEmpty()){
            return new ResponseEntity<>(checkoutServices.savePaymentMethod(userEmail,idOrder,paymentDto), HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping( path = "{idOrder}/item/", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> addItemToOrder(@RequestBody final ItemDto item,
                                                      @PathVariable("idOrder") final Long idOrder,
                                                      final BindingResult bindingResult,
                                                      final Authentication authentication){
        String userEmail = getUserEmailFromAuthentication(authentication);
        if(!userEmail.isEmpty()){

            return new ResponseEntity<>(checkoutServices.addOrderItem(idOrder, item), HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping( path = "{idOrder}/item/{idItem}", method = RequestMethod.PUT)
    public ResponseEntity<OrderDto> updateItemOfOrder(@RequestBody final ItemDto item,
                                                       @PathVariable("idOrder") final Long idOrder,
                                                       @PathVariable("idItem") final Long idItem,
                                                       final BindingResult bindingResult,
                                                       final Authentication authentication){
        String userEmail = getUserEmailFromAuthentication(authentication);
        if(!userEmail.isEmpty()){
            item.setCode(idItem);
            return new ResponseEntity<>(checkoutServices.updateOrderItemStock(idOrder, item), HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping( path = "{idOrder}/item/{idItem}", method = RequestMethod.DELETE)
    public ResponseEntity<OrderDto> deleteItemOfOrder(@PathVariable("idOrder") final Long idOrder,
                                                      @PathVariable("idItem") final Long idItem,
                                                      final Authentication authentication
                                                      ){
        String userEmail = getUserEmailFromAuthentication(authentication);
        if(!userEmail.isEmpty()){

            return new ResponseEntity<>(checkoutServices.deleteOrderItem(idOrder, idItem), HttpStatus.OK);
        }
        return null;
    }

    private String getUserEmailFromAuthentication(Authentication authentication){
        KeycloakPrincipal<KeycloakSecurityContext> kp =
                (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        return kp.getKeycloakSecurityContext().getToken().getEmail();
    }

}
