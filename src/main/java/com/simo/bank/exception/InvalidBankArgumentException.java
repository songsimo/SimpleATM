package com.simo.bank.exception;

public class InvalidBankArgumentException extends RuntimeException {
    public InvalidBankArgumentException(String message) {
        super(message);
    }
}
