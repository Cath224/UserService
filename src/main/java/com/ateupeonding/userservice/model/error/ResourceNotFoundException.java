package com.ateupeonding.userservice.model.error;

public class ResourceNotFoundException extends UserServiceException {

    private static final String ERROR_MESSAGE = "Resource %s not found by %s = %s";


    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format(ERROR_MESSAGE, resource, field, value));
    }
}
