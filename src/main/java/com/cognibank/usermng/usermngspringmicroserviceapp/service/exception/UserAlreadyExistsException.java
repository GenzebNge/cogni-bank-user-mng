package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserAlreadyExistsException - thrown when User already exists in the database
 * @see RuntimeException
 * @see HttpStatus#NOT_ACCEPTABLE
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "User already exists")
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructor to create a new UserAlreadyExistsException()
     * @param e
     */
    public UserAlreadyExistsException(Throwable e) {
        super(e);
    }
}
