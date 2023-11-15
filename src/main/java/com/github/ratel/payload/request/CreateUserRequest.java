package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String confirmPassword;

    @NotEmpty
    @Size(min = 9, max = 20)
    private String phone;

}
