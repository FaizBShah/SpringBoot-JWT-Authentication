package com.example.springjwtauthentication.event.listener;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.event.RegistrationCompleteEvent;
import com.example.springjwtauthentication.service.EmailService;
import com.example.springjwtauthentication.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        String verificationToken = UUID.randomUUID().toString();
        User user = event.getUser();

        verificationTokenService.saveVerificationToken(verificationToken, user);

        String tokenUrl = event.getApplicationUrl() + "/api/v1/auth/verifyRegistration?token=" + verificationToken;

        emailService.sendVerificationTokenEmail(tokenUrl, user.getEmail());
    }
}
