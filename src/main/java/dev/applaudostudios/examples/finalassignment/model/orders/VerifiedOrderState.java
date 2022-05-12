package dev.applaudostudios.examples.finalassignment.model.orders;

import lombok.Setter;

import java.util.UUID;
@Setter
public class VerifiedOrderState implements IOrderState{
    private UUID paymentId;

    @Override
    public void handle(SimpleOrder simpleOrder) {
        if(paymentId != null && !paymentId.toString().isEmpty()){
            UUID uuid = UUID.randomUUID();
            simpleOrder.setVerificationCode(uuid);
        }
    }
}
