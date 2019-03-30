package com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl;

public class UserAlreadyExistsException extends RuntimeException {
    UserAlreadyExistsException(Throwable e) {
        super(e);
    }
}
