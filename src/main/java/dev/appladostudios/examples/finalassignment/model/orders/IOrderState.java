package dev.appladostudios.examples.finalassignment.model.orders;

public interface IOrderState {
    void handle(SimpleOrder simpleOrder);
}
