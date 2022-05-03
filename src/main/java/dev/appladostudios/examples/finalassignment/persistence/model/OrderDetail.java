package dev.appladostudios.examples.finalassignment.web.controller.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional = false)
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Positive
    private int productQuantity;

    @PositiveOrZero
    private double subTotal;

}
