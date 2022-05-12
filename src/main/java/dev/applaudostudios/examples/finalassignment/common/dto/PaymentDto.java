package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends Dto<Integer>{
    //@NotNull(message = "Id can't be null or empty")
    private Integer id;
    //@NotEmpty(message = "The payment method name should be present.")
    private String paymentMethodName;

    private String paymentMethodDescription;
    //@NotEmpty(message = "The payment method code must be specified")
    private UUID token;

}
