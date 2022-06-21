package com.ateupeonding.userservice.controller;

import com.ateupeonding.userservice.model.dto.AuthenticationDto;
import com.ateupeonding.userservice.service.api.AuthenticationService;
import com.ateupeonding.userservice.service.api.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-service/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody AuthenticationDto authenticationDto) {
        String token = authenticationService.login(authenticationDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    @PostMapping("registration")
    public ResponseEntity<Void> register(@RequestBody AuthenticationDto authenticationDto) {
        authenticationService.register(authenticationDto);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }


    @PostMapping("verify")
    public void verify(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        authorizationService.verify(token);
    }

}
