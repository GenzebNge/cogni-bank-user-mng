package com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl;

public class PasswordHashingNotPossibleException extends RuntimeException {
    PasswordHashingNotPossibleException(Throwable e) {
        super(e);
    }
}
