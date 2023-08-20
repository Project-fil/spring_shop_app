package com.github.ratel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequest {

    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 50)
    private String password;

}
