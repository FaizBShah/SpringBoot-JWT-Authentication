package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.event.RegistrationCompleteEvent;
import com.example.springjwtauthentication.model.request.LoginUserRequestBody;
import com.example.springjwtauthentication.model.request.RegisterUserRequestBody;
import com.example.springjwtauthentication.model.response.LoginSuccessResponseMessage;
import com.example.springjwtauthentication.model.response.ResponseMessage;
import com.example.springjwtauthentication.service.AuthService;
import com.example.springjwtauthentication.service.HttpRequestService;
import com.example.springjwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpRequestService httpRequestService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody RegisterUserRequestBody requestBody, final HttpServletRequest request) {
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

        return ResponseEntity.ok().body(new ResponseMessage(true));
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<ResponseMessage> verifyRegistration(@RequestParam("token") String token) {
        authService.verifyRegistration(token);
        return ResponseEntity.ok().body(new ResponseMessage(true));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessResponseMessage> loginUser(@RequestBody LoginUserRequestBody requestBody) {
        String token = authService.loginUser(requestBody.getEmail(), requestBody.getPassword());
        User user = (User) userService.loadUserByUsername(requestBody.getEmail());

        return ResponseEntity.ok().body(new LoginSuccessResponseMessage(
                true,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                token
        ));
    }
}
