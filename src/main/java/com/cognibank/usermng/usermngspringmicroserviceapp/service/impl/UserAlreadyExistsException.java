package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "User already exists")
public class UserAlreadyExistsException extends RuntimeException {
    UserAlreadyExistsException(Throwable e) {
        super(e);
    }
}
