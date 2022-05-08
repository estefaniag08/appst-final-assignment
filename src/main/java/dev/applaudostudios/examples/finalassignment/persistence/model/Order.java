package dev.applaudostudios.examples.finalassignment.persistence.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "order_store")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class Order extends PersistenceEntity{
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

    @ManyToOne
    private User user;
}
