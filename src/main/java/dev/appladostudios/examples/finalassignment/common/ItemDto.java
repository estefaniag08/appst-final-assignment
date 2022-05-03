package dev.appladostudios.examples.finalassignment.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemDto {
    @EqualsAndHashCode.Include
    private final long code;
    private final String name;
    private int units;
    private final Double unitPrice;
}
