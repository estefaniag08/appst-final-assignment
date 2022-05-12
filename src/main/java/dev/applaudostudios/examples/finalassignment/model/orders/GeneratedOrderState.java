package dev.applaudostudios.examples.finalassignment.model.orders;

import java.util.UUID;

public class GeneratedOrderState implements IOrderState{
    @Override
    public void handle(SimpleOrder simpleOrder) {
        if (simpleOrder.orderList.getListOfItems().size() > 0){
            UUID uuid = UUID.randomUUID();
            simpleOrder.setOrderCode(uuid);
        }
    }
}
