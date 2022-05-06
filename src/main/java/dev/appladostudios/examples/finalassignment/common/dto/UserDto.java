package dev.appladostudios.examples.finalassignment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto{
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
