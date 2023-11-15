package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import lombok.*;

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

