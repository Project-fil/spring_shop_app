package com.github.ratel.payload.response;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;

    private String comment;

    private String phone;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private String apartmentNumber;

    private List<UserResponse> users = new ArrayList<>();

    private String createdDate;

    private String lastModifiedDate;

}
