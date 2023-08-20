package com.github.ratel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private Long id;

    private String firstname;

    private String lastname;

    private String phone;

    private String country;

    private String city;

    private String street;

    private int houseNumber;

    private int apartmentNumber;

}
