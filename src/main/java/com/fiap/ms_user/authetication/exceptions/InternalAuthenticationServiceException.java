package com.fiap.ms_user.authetication.exceptions;

public class InternalAuthenticationServiceException extends RuntimeException{
    public InternalAuthenticationServiceException() {
    }

    public InternalAuthenticationServiceException(String message) {
        super(message);
    }

    public InternalAuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
