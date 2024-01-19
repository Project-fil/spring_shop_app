package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import lombok.*;

import java.util.List;

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

    private List<OrderResponse> orders;

    private Roles role;

    private UserVerificationStatus verification;

    private Boolean removed;

    private String createAt;

    private String updateAt;

}
