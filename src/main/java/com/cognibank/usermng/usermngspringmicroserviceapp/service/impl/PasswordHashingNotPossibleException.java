package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

public class PasswordHashingNotPossibleException extends RuntimeException {
    PasswordHashingNotPossibleException(Throwable e) {
        super(e);
    }
}
