package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.model.ConfType;
import com.ateupeonding.userservice.model.dto.AuthenticationDto;
import com.ateupeonding.userservice.model.entity.Seed;
import com.ateupeonding.userservice.model.entity.User;
import com.ateupeonding.userservice.service.api.AuthenticationService;
import com.ateupeonding.userservice.service.api.EncryptionService;
import com.ateupeonding.userservice.service.api.SeedService;
import com.ateupeonding.userservice.service.api.UserService;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private SeedService seedService;
    @Autowired
    private EncryptionService encryptionService;

    @Value("${spring.application.name}")
    private String issuer;

    @Override
    public String login(AuthenticationDto authenticationDto) {
        String login = authenticationDto.getLogin();
        String password = authenticationDto.getPassword();

        User user = userService.getByLogin(login);

        if (user == null || password == null) {
            throw new RuntimeException();
        }

        UUID userId = user.getId();

        Seed seed = seedService.getByReferenceId(userId);

        byte[] value = encryptionService.decrypt(seed.getValue(), ConfType.VAL);

        String passwordHash = encryptionService.hash(password.getBytes(StandardCharsets.UTF_8), ConfType.ENC, Collections.singleton(value));

        if (!Objects.equals(passwordHash, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(user.getLogin())
                .expirationTime(Date.from(OffsetDateTime.now().toInstant()))
                .build();
        JWT jwt = new PlainJWT(claimsSet);
        return jwt.serialize();

    }

    @Override
    public void register(AuthenticationDto authenticationDto) {
        User user = new User();
        user.setLogin(authenticationDto.getLogin());
        user.setPassword(authenticationDto.getPassword());
        userService.create(user);
    }
}
