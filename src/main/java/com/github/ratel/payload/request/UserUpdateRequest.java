package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank
    private long id;

    private String firstname;

    private String lastname;

    private String phone;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private String apartmentNumber;

}
