package com.ateupeonding.userservice.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserServiceBadRequestException extends ResponseStatusException {
    public UserServiceBadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
