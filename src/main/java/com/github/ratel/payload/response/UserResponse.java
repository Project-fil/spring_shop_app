package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private FileEntityResponse image;

    private AddressResponse address;

    private CartResponse cart;

    private Roles role;

    private UserVerificationStatus verification;

    private boolean removed;

    private Date createAt;

    private Date updateAt;

}
