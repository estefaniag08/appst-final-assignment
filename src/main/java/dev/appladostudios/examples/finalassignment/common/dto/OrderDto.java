package dev.appladostudios.examples.finalassignment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto{
    protected List<ItemDto> orderItems;
    private UUID reservationCode;
    private UUID verificationCode;
    private UUID orderCode;
    private AddressDto address;
    private PaymentDto payment;
}
