package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.entity.VerificationToken;
import com.example.springjwtauthentication.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public void saveVerificationToken(String verificationToken, User user) {
        verificationTokenRepository.save(new VerificationToken(verificationToken, user));
    }
}
