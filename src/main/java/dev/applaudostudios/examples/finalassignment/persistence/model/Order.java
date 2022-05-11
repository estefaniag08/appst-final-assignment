package dev.applaudostudios.examples.finalassignment.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.applaudostudios.examples.finalassignment.persistence.UUIDValidator;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @PositiveOrZero(message = "Total can't be zero or less.")
    private double orderTotal;
    @UUIDValidator
    private UUID orderCode;
    @UUIDValidator
    private UUID reservationCode;
    @UUIDValidator
    private UUID dispatchCode;
    @UUIDValidator
    private UUID verificationCode;

    @OneToMany( mappedBy="order", orphanRemoval = true)
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonManagedReference
    @NotEmpty(message = "Order item list can't be empty or null.")
    private List<OrderDetail> orderItems;

    @ManyToOne
    //@NotNull(message = "Address cannot be null or empty.")
    private Address address;

    @ManyToOne
    //@NotEmpty(message = "Payment method cannot be null or empty.")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @NotNull(message = "Order user can't be null")
    private User user;
}
