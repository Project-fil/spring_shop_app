package com.github.ratel.dto;

import com.github.ratel.entity.enums.EntityStatus;
import com.github.ratel.entity.enums.UserVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegDto {

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 2, max = 70)
    private String password;

    @NotNull
    @Min(value = 0)
    private String phone;

    @NotNull
    private String address;

    @NotNull
    @PastOrPresent
    private Date createdAt = new Date();

    private Set<RoleDto> roles;

    @NotNull
    private UserVerificationStatus verification = UserVerificationStatus.UNVERIFIED;

    public UserRegDto(String firstname, String lastname, String email, String password, String phone, String address, Date createdAt,
                      UserVerificationStatus verification, EntityStatus status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.verification = verification;
    }
}
