package dev.appladostudios.examples.finalassignment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private int id;
    private String paymentMethodName;
    private String paymentMethodDescription;
}
