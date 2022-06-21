package com.ateupeonding.userservice.service.api;

import com.nimbusds.jwt.JWT;

public interface JwtService {

    String generate();

    JWT parse(String token);

}
