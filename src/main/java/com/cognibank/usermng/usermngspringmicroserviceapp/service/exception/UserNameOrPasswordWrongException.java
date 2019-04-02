package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserNameOrPasswordWrongException - thrown when User details provided does not match the details in the database
 * @see RuntimeException
 * @see HttpStatus#UNAUTHORIZED
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User name or password wrong.")
public class UserNameOrPasswordWrongException extends RuntimeException {
}
