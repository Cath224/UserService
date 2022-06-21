package com.ateupeonding.userservice.service.api;

import com.ateupeonding.userservice.model.dto.AuthenticationDto;

public interface AuthenticationService {

    String login(AuthenticationDto authenticationDto);

    void register(AuthenticationDto authenticationDto);
}
