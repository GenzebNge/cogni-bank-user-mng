package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserDetailsUpdateException - thrown when unable to update User details
 * @see RuntimeException
 * @see HttpStatus#FORBIDDEN
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User details update failed.")
public class UserDetailsUpdateException extends RuntimeException {
    /**
     * Constructor to create a new UserDetailsUpdateException()
     * @param message
     */
    public UserDetailsUpdateException(String message) {
        super(message);
    }
}
