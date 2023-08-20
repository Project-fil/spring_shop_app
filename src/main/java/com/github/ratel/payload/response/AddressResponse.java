package com.github.ratel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;

    private String comment;

    private String phone;

    private String country;

    private String city;

    private String street;

    private int houseNumber;

    private int apartmentNumber;

    private List<UserResponse> userAddress = new ArrayList<>();

    private Date createdDate;

    private Date lastModifiedDate;

    public AddressResponse(Long id,
                           String comment,
                           String phone,
                           String country,
                           String city,
                           String street,
                           int houseNumber,
                           int apartmentNumber,
                           Date createdDate,
                           Date lastModifiedDate
    ) {
        this.id = id;
        this.comment = comment;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
