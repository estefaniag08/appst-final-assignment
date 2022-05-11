package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "The address cannot be empty.")
    private String address;

    private String additionalInformation;

    @Size(min = 5, max = 7, message = "Postal code should be between 5 and 7 characters.")
    private String postalCode;

    @NotEmpty(message = "Name of the receiver is required.")
    private String receiverName;

    @ManyToOne
    @JsonBackReference
    private User user;

}
