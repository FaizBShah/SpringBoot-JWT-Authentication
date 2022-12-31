package com.example.springjwtauthentication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public void sendVerificationTokenEmail(String tokenUrl, String email) {
        log.info("Click on the link to verify your email {}: {}", email, tokenUrl);
    }
}
