package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User name or password wrong.")
public class UserNameOrPasswordWrongException extends RuntimeException {
}
