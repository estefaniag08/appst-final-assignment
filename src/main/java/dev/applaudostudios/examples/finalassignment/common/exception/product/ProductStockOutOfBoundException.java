package dev.applaudostudios.examples.finalassignment.common.exception.product;

public class ProductStockOutOfBoundException extends ProductRelatedException{

    public ProductStockOutOfBoundException(String message) {
        super(message);
    }
}
