package dev.appladostudios.examples.finalassignment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto{
    private Long id;
    private String productName;
    private int stock;
    private double pricePerUnit;
}
