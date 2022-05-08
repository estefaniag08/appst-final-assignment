package dev.applaudostudios.examples.finalassignment.model.orders;

import lombok.Setter;

import java.util.UUID;
@Setter
public class DispatchOrderState implements IOrderState{
    private String postalCode;
    @Override
    public void handle(SimpleOrder simpleOrder) {
        if(!postalCode.trim().isEmpty()){
            UUID uuid = UUID.randomUUID();
            simpleOrder.setDispatchCode(uuid);
        }
    }
}
