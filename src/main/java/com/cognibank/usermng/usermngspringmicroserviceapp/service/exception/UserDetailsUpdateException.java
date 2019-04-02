package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User details update failed.")
public class UserDetailsUpdateException extends RuntimeException {
    public UserDetailsUpdateException(String message) {
        super(message);
    }
}
