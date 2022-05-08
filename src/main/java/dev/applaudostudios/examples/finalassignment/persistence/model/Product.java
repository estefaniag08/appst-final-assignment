package dev.applaudostudios.examples.finalassignment.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
public class Product extends PersistenceEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty( message = "The product name cannot be null or empty.")
    private String productName;

    @NotEmpty( message = "The product stock cannot be null or empty.")
    @PositiveOrZero
    private int stock;

    @NotEmpty( message = "The product price cannot be null or empty.")
    @PositiveOrZero
    private double pricePerUnit;

}
