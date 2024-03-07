package com.fiap.ms_user.authetication.exceptions;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException() {
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
