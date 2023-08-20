package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private Long userId;
    private String userName;
    private Roles role;

}

