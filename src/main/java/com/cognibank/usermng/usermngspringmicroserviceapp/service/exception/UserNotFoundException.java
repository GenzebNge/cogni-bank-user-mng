package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserNotFoundException - thrown when User is not found on the Database
 * @see RuntimeException
 * @see HttpStatus#NOT_FOUND
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }
}
