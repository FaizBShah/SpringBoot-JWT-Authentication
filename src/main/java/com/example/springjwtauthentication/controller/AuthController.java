package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.event.RegistrationCompleteEvent;
import com.example.springjwtauthentication.model.request.LoginUserRequestBody;
import com.example.springjwtauthentication.model.request.RegisterUserRequestBody;
import com.example.springjwtauthentication.service.AuthService;
import com.example.springjwtauthentication.service.HttpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpRequestService httpRequestService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterUserRequestBody requestBody, final HttpServletRequest request) {
        User user = authService.registerUser(
                requestBody.getFirstName(),
                requestBody.getLastName(),
                requestBody.getEmail(),
                requestBody.getPassword()
        );

        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                httpRequestService.applicationUrl(request)
        ));

        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        return authService.verifyRegistration(token);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginUserRequestBody requestBody) {
        return authService.loginUser(requestBody.getEmail(), requestBody.getPassword());
    }
}
