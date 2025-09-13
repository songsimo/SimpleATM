package com.simo.bank.domain;

import com.simo.bank.exception.InvalidBankArgumentException;

public class VirtualBank {
    public static final int VALID_CARD_LENGTH = 16;
    public static final int VALID_PIN_LENGTH = 4;

    public static final String EMPTY_MESSAGE = "Card 혹은 PIN 번호를 확인해주세요.";
    public static final String CARD_CHECK_MESSAGE = "Card 번호를 확인해주세요.";
    public static final String PIN_CHECK_MESSAGE = "PIN 번호를 확인해주세요.";
    public static final String VALID_CARD_NUMBER = "1234567891011111";
    public static final String VALID_PIN_NUMBER = "1234";

    public VirtualBank() {
    }

    public void validate(String cardNumber, String pin) {
        if(isEmpty(cardNumber) || isEmpty(pin)) {
            throw new InvalidBankArgumentException(EMPTY_MESSAGE);
        }

        if(isInvalidCardLength(cardNumber) || isInvalidPinLength(pin)) {
            throw new InvalidBankArgumentException(CARD_CHECK_MESSAGE);
        }

        if(isInvalidCardNumber(cardNumber) || isInvalidPinNumber(pin)) {
            throw new InvalidBankArgumentException(PIN_CHECK_MESSAGE);
        }
    }

    public boolean isValidCard(String cardNumber) {
        if(isEmpty(cardNumber)) { return false; }

        return !isInvalidCardLength(cardNumber) && !isInvalidCardNumber(cardNumber);
    }

    public boolean isValidPin(String pin) {
        if(isEmpty(pin)) { return false; }

        return !isInvalidPinLength(pin) && !isInvalidPinNumber(pin);
    }

    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isInvalidCardLength(String cardNumber) {
        return cardNumber.length() != VALID_CARD_LENGTH;
    }

    private boolean isInvalidCardNumber(String cardNumber) {
        return !VALID_CARD_NUMBER.equals(cardNumber);
    }

    private boolean isInvalidPinLength(String pin) {
        return pin.length() != VALID_PIN_LENGTH;
    }

    private boolean isInvalidPinNumber(String pin) {
        return !VALID_PIN_NUMBER.equals(pin);
    }
}
