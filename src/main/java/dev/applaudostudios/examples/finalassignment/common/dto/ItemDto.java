package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemDto extends Dto<Long> {
    @EqualsAndHashCode.Include
    //@NotNull(message = "The item code can't be null or empty.")
    @PositiveOrZero(message = "The item code must be a positive number.")
    private Long code;
    private String name;
    @NotNull(message = "The item unit can't be null or empty.")
    @PositiveOrZero(message = "The item code must be a positive number.")
    private int units;
    private Double unitPrice;

    @Override
    public Long getId() {
        return this.code;
    }
}
