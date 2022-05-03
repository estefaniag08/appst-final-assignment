package dev.appladostudios.examples.finalassignment.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "The address cannot be null")
    @NotEmpty(message = "The address cannot be empty")
    private String address;

    private String additionalInformation;

    @Size(min = 5, max = 7)
    private String postalCode;

    @NotEmpty(message = "Name of the receiver is required.")
    private String receiverName;

    @ManyToOne
    private User user;

}
