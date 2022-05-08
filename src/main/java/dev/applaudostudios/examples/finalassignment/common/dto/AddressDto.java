package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class AddressDto extends Dto<Integer> {
    private final Integer id;
    @NotEmpty(message = "Address cannot be null or empty.")
    @Size(min = 4, message = "Address should be more longer (min 4). ")
    private final String address;
    private final String additionalInformation;

    @Size(min = 5, max = 7, message = "Postal code should be between 5 and 7 characters.")
    private final String postalCode;
    @NotEmpty(message = "Receiver name must be specified.")
    private final String receiverName;

}
