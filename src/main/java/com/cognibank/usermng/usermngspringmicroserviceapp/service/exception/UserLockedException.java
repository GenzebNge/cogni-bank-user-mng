package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserLockedException - thrown when user is locked or forbidden to access the resource
 * @see RuntimeException
 * @see HttpStatus#FORBIDDEN
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User is locked.")
public class UserLockedException extends RuntimeException {
}
