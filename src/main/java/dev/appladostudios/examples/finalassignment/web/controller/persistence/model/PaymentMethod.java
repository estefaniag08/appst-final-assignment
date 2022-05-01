package dev.appladostudios.examples.finalassignment.web.controller.persistence.model;

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

    private UUID token;

    @ManyToOne
    private User user;

}
