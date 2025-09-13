package com.simo.exception;

public class InvalidBankArgumentException extends RuntimeException {
    public InvalidBankArgumentException(String message) {
        super(message);
    }
}
