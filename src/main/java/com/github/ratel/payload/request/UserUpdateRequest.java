package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @NotNull
    private Long id;

    private String firstname;

    private String lastname;

    private String phone;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private String apartmentNumber;

}
