package dev.appladostudios.examples.finalassignment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private int id;
    private String address;
    private String additionalInformation;
    private String postalCode;
    private String receiverName;
}
