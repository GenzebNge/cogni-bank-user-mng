package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User is locked.")
public class UserLockedException extends RuntimeException {
}
