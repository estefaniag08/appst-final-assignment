package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Payment method name cannot be null or empty.")
    private String paymentMethodName;

    @NotEmpty(message = "Payment description cannot be null or empty.")
    private String paymentMethodDescription;

    private UUID token;

    @ManyToOne
    @JsonBackReference
    private User user;

}
