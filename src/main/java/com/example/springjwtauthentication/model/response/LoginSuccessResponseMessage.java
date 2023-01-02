package com.example.springjwtauthentication.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class LoginSuccessResponseMessage extends UserResponseMessage {

    private final String token;

    public LoginSuccessResponseMessage(boolean success, String firstName, String lastName, String email, String token) {
        super(success, firstName, lastName, email);
        this.token = token;
    }
}
