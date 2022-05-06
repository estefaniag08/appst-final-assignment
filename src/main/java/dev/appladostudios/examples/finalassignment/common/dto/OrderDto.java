package dev.appladostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends Dto<Long>{
    private Long id;
    @NotEmpty(message = "The list of items cannot be empty.")
    protected List<ItemDto> orderItems;

    private UUID reservationCode;
    private UUID verificationCode;
    private UUID orderCode;

    private double orderTotal;

    //@NotEmpty(message = "Address must be present.")
    private AddressDto address;
    //@NotEmpty(message = "Payment method must be present.")
    private PaymentDto payment;

    private UserDto user;
}
