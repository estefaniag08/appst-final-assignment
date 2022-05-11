package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemDto extends Dto<Long> {
    @EqualsAndHashCode.Include
    @NotEmpty(message = "The item code can't be null or empty.")
    private Long code;
    private String name;
    @NotEmpty(message = "The item unit can't be null or empty.")
    private int units;
    private Double unitPrice;

    @Override
    public Long getId() {
        return this.code;
    }
}
