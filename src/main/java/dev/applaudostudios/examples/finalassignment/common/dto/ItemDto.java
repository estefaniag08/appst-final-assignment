package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemDto extends Dto<Long> {
    @EqualsAndHashCode.Include
    private Long code;
    private String name;
    private int units;
    private Double unitPrice;

    @Override
    public Long getId() {
        return this.code;
    }
}
