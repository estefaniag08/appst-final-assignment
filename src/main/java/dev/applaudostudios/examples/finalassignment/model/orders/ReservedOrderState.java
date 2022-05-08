package dev.applaudostudios.examples.finalassignment.model.orders;

import java.util.UUID;

public class ReservedOrderState implements IOrderState{

    @Override
    public void handle(SimpleOrder simpleOrder) {
        if (simpleOrder.orderList.getTotal() > 0.0){
            UUID uuid = UUID.randomUUID();
            simpleOrder.setReservationCode(uuid);
        }
    }
}
