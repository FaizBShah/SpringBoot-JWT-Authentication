package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.entity.UserRole;
import com.example.springjwtauthentication.entity.VerificationToken;
import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String firstName, String lastName, String email, String password) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.ROLE_USER)
                .locked(false)
                .enabled(false)
                .build();

        return userRepository.save(user);
    }

    public String verifyRegistration(String token) {
        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid Verification Token"));

        if (verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Verification Token Expired");
        }

        if (verificationToken.getUser().isEnabled()) {
            throw new IllegalStateException("User is already enabled");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return "User Verified Successfully";
    }
}
