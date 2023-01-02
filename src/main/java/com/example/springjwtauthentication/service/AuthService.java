package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.entity.UserRole;
import com.example.springjwtauthentication.entity.VerificationToken;
import com.example.springjwtauthentication.exception.AppException;
import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.repository.VerificationTokenRepository;
import com.example.springjwtauthentication.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String firstName, String lastName, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "User Already Exists");
        }

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

    public void verifyRegistration(String token) {
        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Invalid Verification Token"));

        if (verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Verification Token Expired");
        }

        if (verificationToken.getUser().isEnabled()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "User is already enabled");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }

    public String loginUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return jwtUtils.generateJwtToken(authentication);
    }
}
