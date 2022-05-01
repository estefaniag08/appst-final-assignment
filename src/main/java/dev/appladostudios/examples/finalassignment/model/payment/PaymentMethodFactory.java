package dev.appladostudios.examples.finalassignment.model.payment;

public class PaymentMethodFactory {

    public PaymentMethod getPaymentMethod(PaymentMethodOption selectedPayment){
        switch (selectedPayment){
            case CREDIT_CARD -> {
               return new CreditCardPayment();
            }
            case PAYPAL -> {
                return new PaypalPayment();
            }
            default -> {
                return null;
            }
        }
    }

    public enum PaymentMethodOption{
        CREDIT_CARD, PAYPAL
    }
}
