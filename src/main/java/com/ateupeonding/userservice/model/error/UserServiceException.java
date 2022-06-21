package com.ateupeonding.userservice.model.error;

public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
