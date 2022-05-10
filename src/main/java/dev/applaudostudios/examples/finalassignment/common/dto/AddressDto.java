package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto extends Dto<Integer> {
    @NotEmpty(message = "The id address can't be null or empty.")
    private Integer id;
    //@NotEmpty(message = "Address cannot be null or empty.")
    //@Size(min = 4, message = "Address should be more longer (min 4). ")
    private String address;
    private String additionalInformation;

    //@Size(min = 5, max = 7, message = "Postal code should be between 5 and 7 characters.")
    private String postalCode;
    //@NotEmpty(message = "Receiver name must be specified.")
    private String receiverName;
}
