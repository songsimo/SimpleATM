package com.simo.bank.domain;

public interface PinValidator {
    void validatePin(String cardNumber, String pin);
}
