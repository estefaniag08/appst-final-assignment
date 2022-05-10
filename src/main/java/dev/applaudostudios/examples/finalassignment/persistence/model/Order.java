package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

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
@Getter
public class Order extends PersistenceEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private double orderTotal;

    private UUID orderCode;

    private UUID reservationCode;

    private UUID dispatchCode;

    private UUID verificationCode;

    //@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OneToMany( mappedBy="order", orphanRemoval = true)
    @JsonManagedReference
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
