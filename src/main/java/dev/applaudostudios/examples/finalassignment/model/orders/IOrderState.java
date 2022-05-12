package dev.applaudostudios.examples.finalassignment.model.orders;

public interface IOrderState {
    void handle(SimpleOrder simpleOrder);
}
