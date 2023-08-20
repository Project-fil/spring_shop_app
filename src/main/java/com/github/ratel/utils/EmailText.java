package com.github.ratel.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailText {

    @Value("${app.email.text}")
    private String textMessageEmail;

    @Value("${app.email.domain}")
    private String hostDomain;

    public String regLetter(String firstName, String lastName, String token) {
        return "Hello"
                + " "
                + firstName
                + " "
                + lastName
                + "."
                + "\n"
                + this.textMessageEmail
                + "verification"
                + "\n"
                + this.hostDomain
                + "/verification?token="
                + token;
    }

    public String confirmPass(String firstName, String lastName, String token) {
        return "Hello"
                + " "
                + firstName
                + " "
                + lastName
                + "."
                + "\n"
                + this.textMessageEmail
                + " password change confirmation"
                + "\n"
                + this.hostDomain
                + "/password?token="
                + token;
    }

}
