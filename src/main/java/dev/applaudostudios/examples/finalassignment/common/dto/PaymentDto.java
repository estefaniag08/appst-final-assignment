package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor

public class PaymentDto extends Dto<Integer>{
    private Integer id;
    @NotEmpty(message = "The payment method name should be present.")
    private String paymentMethodName;

    @NotEmpty(message = "The payment method type must be specified.")
    private String paymentMethodType;

    @NotEmpty(message = "The payment method code must be specified")
    private UUID paymentMethodCode;

    private String paymentMethodDescription;
}
