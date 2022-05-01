package dev.appladostudios.examples.finalassignment.model.orders;

import java.util.UUID;

public class VerifiedOrderState implements IOrderState{
    @Override
    public void handle(Order order) {
        UUID uuid = UUID.randomUUID();
        order.setVerificationCode(uuid);
    }
}
