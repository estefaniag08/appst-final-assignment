package dev.appladostudios.examples.finalassignment.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "order_store")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private double orderTotal;

    private UUID reservationCode;

    private UUID verification;

    private UUID orderCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order")
    private List<OrderDetail> orderItems;

    @ManyToOne
    @NotEmpty(message = "Address cannot be null or empty.")
    private Address address;

    @ManyToOne
    @NotEmpty(message = "Payment method cannot be null or empty.")
    private PaymentMethod paymentMethod;
}
