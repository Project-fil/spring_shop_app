package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private FileEntityResponse image;

    private AddressResponse address;

    private Roles role;

    private UserVerificationStatus verification;

    private boolean removed;

    private Date createAt;

    private Date updateAt;

    public UserResponse(Long id, String firstname, String lastname, String email, FileEntityResponse image, AddressResponse address, Roles role, UserVerificationStatus verification) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.image = image;
        this.address = address;
        this.role = role;
        this.verification = verification;
    }

    public UserResponse(Long id, String firstname, String lastname, String email, Roles role, UserVerificationStatus verification) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.verification = verification;
    }
}
