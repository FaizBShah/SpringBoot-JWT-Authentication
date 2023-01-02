package com.example.springjwtauthentication.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserResponseMessage extends ResponseMessage {

    private final String firstName;
    private final String lastName;
    private final String email;

    public UserResponseMessage(boolean success, String firstName, String lastName, String email) {
        super(success);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
