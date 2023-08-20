package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public UserResponse(Long id, String firstname, String lastname, String email, Roles role, UserVerificationStatus verification, boolean removed) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.verification = verification;
        this.removed = removed;
    }
}
