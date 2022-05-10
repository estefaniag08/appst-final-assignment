package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Positive
    private int productQuantity;

    @PositiveOrZero
    private double subTotal;

}
