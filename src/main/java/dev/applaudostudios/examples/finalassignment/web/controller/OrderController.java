package dev.applaudostudios.examples.finalassignment.web.controller;

import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.dto.PaymentDto;
import dev.applaudostudios.examples.finalassignment.common.exception.RequestException;
import dev.applaudostudios.examples.finalassignment.service.CheckoutService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    @Autowired
    CheckoutService checkoutServices;

    @RequestMapping(method = RequestMethod.GET, path = "{idOrder}")
    public ResponseEntity<?> getOrder(@PathVariable("idOrder") final Long idOrder) {

        return new ResponseEntity<>(checkoutServices.findOrderById(idOrder), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody @Valid final OrderDto orderDto,
                                         final BindingResult bindingResult,
                                         final Authentication authentication) {
        if (bindingResult.hasErrors()) {
            System.out.println("Entra en errores.");
            List<ObjectError> errors = bindingResult.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new RequestException("Error in some fields.", errors);
        }
        String userEmail = getUserEmailFromAuthentication(authentication);
        return new ResponseEntity<>(checkoutServices.createOrder(userEmail, orderDto), HttpStatus.OK);


    }

    @RequestMapping(path = "{idOrder}/address", method = RequestMethod.PUT)
    public ResponseEntity<?> saveAddressOfOrder(@RequestBody @Valid final AddressDto addressDto,
                                                @PathVariable("idOrder") final Long idOrder,
                                                final BindingResult bindingResult,
                                                final Authentication authentication) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new RequestException("Error in some fields.", errors);
        }
        String userEmail = getUserEmailFromAuthentication(authentication);
        return new ResponseEntity<>(checkoutServices.saveAddress(userEmail, idOrder, addressDto), HttpStatus.OK);

    }

    @RequestMapping(path = "{idOrder}/payment", method = RequestMethod.PUT)
    public ResponseEntity<?> savePaymentOfOrder(@RequestBody final @Valid PaymentDto paymentDto,
                                                @PathVariable("idOrder") final Long idOrder,
                                                final BindingResult bindingResult,
                                                final Authentication authentication) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new RequestException("Error in some fields.", errors);
        }
        String userEmail = getUserEmailFromAuthentication(authentication);
        return new ResponseEntity<>(checkoutServices.savePaymentMethod(userEmail, idOrder, paymentDto), HttpStatus.OK);

    }

    @RequestMapping(path = "{idOrder}/item/", method = RequestMethod.POST)
    public ResponseEntity<?> addItemToOrder(@RequestBody @Valid final ItemDto item,
                                            @PathVariable("idOrder") final Long idOrder,
                                            final BindingResult bindingResult,
                                            final Authentication authentication) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new RequestException("Error in some fields.", errors);
        }
        String userEmail = getUserEmailFromAuthentication(authentication);
        return new ResponseEntity<>(checkoutServices.addOrderItem(userEmail, idOrder, item), HttpStatus.OK);

    }

    @RequestMapping(path = "{idOrder}/item/{idItem}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateItemOfOrder(@RequestBody @Valid final ItemDto item,
                                               @PathVariable("idOrder") final Long idOrder,
                                               @PathVariable("idItem") final Long idItem,
                                               final BindingResult bindingResult,
                                               final Authentication authentication) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new RequestException("Error in some fields.", errors);
        }
        String userEmail = getUserEmailFromAuthentication(authentication);
        item.setCode(idItem);
        return new ResponseEntity<>(checkoutServices.updateOrderItemStock(userEmail, idOrder, item), HttpStatus.OK);
    }


    @RequestMapping(path = "{idOrder}/item/{idItem}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemOfOrder(@PathVariable("idOrder") final Long idOrder,
                                               @PathVariable("idItem") final Long idItem,
                                               final Authentication authentication
    ) {
        String userEmail = getUserEmailFromAuthentication(authentication);
        return new ResponseEntity<>(checkoutServices.deleteOrderItem(userEmail, idOrder, idItem), HttpStatus.OK);
    }

    private String getUserEmailFromAuthentication(Authentication authentication) {
        KeycloakPrincipal<KeycloakSecurityContext> kp =
                (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        return kp.getKeycloakSecurityContext().getToken().getEmail();
    }

}
