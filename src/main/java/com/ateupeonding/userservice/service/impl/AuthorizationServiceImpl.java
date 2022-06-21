package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.service.api.AuthorizationService;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Value("${spring.application.name}")
    private String issuer;

    @Override
    public void verify(String token) {
        token = token.replace("Bearer ", "");
        try {
            JWTClaimsSet claimsSet = JWTParser.parse(token)
                    .getJWTClaimsSet();
            String issuer = claimsSet
                    .getIssuer();
            if (!Objects.equals(this.issuer, issuer)) {
                throw new RuntimeException();
            }
            Date date = claimsSet.getExpirationTime();
            if (OffsetDateTime.now().isBefore(date.toInstant().atOffset(ZoneOffset.UTC))) {
                throw new RuntimeException();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
