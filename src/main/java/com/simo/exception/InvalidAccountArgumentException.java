package com.simo.exception;

public class InvalidAccountArgumentException extends RuntimeException {
    public InvalidAccountArgumentException(String message) {
        super(message);
    }
}
