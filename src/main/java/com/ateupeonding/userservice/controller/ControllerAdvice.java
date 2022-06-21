package com.ateupeonding.userservice.controller;

import com.ateupeonding.userservice.model.dto.error.ErrorResponse;
import com.ateupeonding.userservice.model.entity.User;
import com.ateupeonding.userservice.model.error.ResourceNotFoundException;
import com.ateupeonding.userservice.model.error.UserServiceException;
import com.ateupeonding.userservice.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        String login = (String) request.getAttribute("USER_LOGIN");
        response.setUser(login);
        response.setCreatedTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handlerUserServiceException(UserServiceException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        String login = (String) request.getAttribute("USER_LOGIN");
        response.setUser(login);
        response.setCreatedTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

  //  @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handlerRuntimeException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        String login = (String) request.getAttribute("USER_LOGIN");
        response.setUser(login);
        response.setCreatedTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
