package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TokenResponse {

    private String token;

    private Long userId;

    private String userName;

    private Roles role;

}

