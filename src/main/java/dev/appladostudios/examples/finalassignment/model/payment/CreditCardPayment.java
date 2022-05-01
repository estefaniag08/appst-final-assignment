package dev.appladostudios.examples.finalassignment.model.payment;

import java.util.UUID;

public class CreditCardPayment implements PaymentMethod{

    @Override
    public boolean isValid(String paymentInformation) {
        int cardNumber = Integer.parseInt(paymentInformation);
        return cardNumber >= 4111 && cardNumber <= 4200;
    }

    @Override
    public boolean pay(String paymentInformation, UUID paymentOrderId) {
        //Hardcoded, should redirect to external service.
        return isValid(paymentInformation);
    }
}
