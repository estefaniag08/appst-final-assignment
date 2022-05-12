package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Dto<Long> {
    private Long id;
    private String productName;
    private int stock;
    private double pricePerUnit;
}
