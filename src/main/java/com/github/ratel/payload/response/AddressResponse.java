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

    private List<UserResponse> userAddress = new ArrayList<>();

    private Date createdDate;

    private Date lastModifiedDate;

    public AddressResponse(Long id,
                           String comment,
                           String phone,
                           String country,
                           String city,
                           String street,
                           String houseNumber,
                           String apartmentNumber,
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
