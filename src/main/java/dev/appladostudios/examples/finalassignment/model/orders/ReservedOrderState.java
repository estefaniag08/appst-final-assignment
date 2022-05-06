package dev.appladostudios.examples.finalassignment.model.orders;

import java.util.UUID;

public class ReservedOrderState implements IOrderState{

    @Override
    public void handle(SimpleOrder simpleOrder) {
        UUID uuid = UUID.randomUUID();
        simpleOrder.setReservationCode(uuid);
    }
}
