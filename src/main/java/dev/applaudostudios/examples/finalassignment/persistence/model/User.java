package dev.applaudostudios.examples.finalassignment.persistence.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user_store")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Email( message = "Invalid email format.")
    @NotNull( message = "Email is required.")
    @NaturalId
    private String email;

    @NotEmpty(message = "Firstname is required.")
    @Size(max = 500, message = "Max length of the first name is 500.")
    private String firstName;

    @NotEmpty(message = "Lastname is required.")
    @Size(max = 500, message = "Max length of the last name is 500.")
    private String lastName;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<PaymentMethod> paymentMethods;
}
