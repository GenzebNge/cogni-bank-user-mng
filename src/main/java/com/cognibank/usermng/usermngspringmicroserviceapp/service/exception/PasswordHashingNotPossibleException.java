package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

public class PasswordHashingNotPossibleException extends RuntimeException {
    public PasswordHashingNotPossibleException(Throwable e) {
        super(e);
    }
}
