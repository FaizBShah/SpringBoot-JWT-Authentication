package com.example.springjwtauthentication.event;

import com.example.springjwtauthentication.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);

        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
