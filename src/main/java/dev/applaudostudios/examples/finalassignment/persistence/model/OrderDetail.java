package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

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
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonBackReference
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Positive
    private int productQuantity;

    @PositiveOrZero
    private double subTotal;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                '}';
    }
}
