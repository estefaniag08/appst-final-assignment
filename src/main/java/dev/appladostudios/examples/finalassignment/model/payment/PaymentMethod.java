package dev.appladostudios.examples.finalassignment.model.payment;

import java.util.UUID;

public interface PaymentMethod {
    boolean isValid(String paymentInformation);
    boolean pay(String paymentInformation, UUID paymentOrderId);
}
