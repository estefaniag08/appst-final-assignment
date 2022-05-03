package dev.appladostudios.examples.finalassignment.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.UUID;

@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentMethodId;

    @NotEmpty(message = "Payment method name cannot be null or empty.")
    private String paymentMethodName;

    @NotEmpty(message = "Payment description cannot be null or empty.")
    private String paymentMethodDescription;

    private UUID token;

    @ManyToOne
    private User user;

}
