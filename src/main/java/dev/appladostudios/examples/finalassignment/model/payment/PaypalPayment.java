package dev.appladostudios.examples.finalassignment.model.payment;

import java.util.UUID;

public class PaypalPayment implements PaymentMethod{
    @Override
    public boolean isValid(String paymentInformation) {
        return paymentInformation.trim().length() > 0;
    }

    @Override
    public boolean pay(String paymentInformation, UUID paymentOrderId) {
        //Hardcoded, should redirect to external service.
        return isValid(paymentInformation);
    }
}
