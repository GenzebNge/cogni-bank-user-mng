package com.cognibank.usermng.usermngspringmicroserviceapp.service.exception;

/**
 * PasswordHashingNotPossibleException - thrown when hashing the password is not possible
 * @see RuntimeException
 */
public class PasswordHashingNotPossibleException extends RuntimeException {
    /**
     * Constructor to create a new PasswordHashingNotPossibleException()
     * @param e
     */
    public PasswordHashingNotPossibleException(Throwable e) {
        super(e);
    }
}
