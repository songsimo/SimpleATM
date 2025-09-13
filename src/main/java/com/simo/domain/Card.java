package com.simo.domain;

import java.util.Objects;

public class Card {
    public static final int VALID_CARD_LENGTH = 16;
    public static final int VALID_PIN_LENGTH = 4;

    private static final String INVALID_CARD_LENGTH = "카드번호 길이는 16자리 입니다.";
    private static final String INVALID_PIN_LENGTH = "PIN 번호 길이는 4자리 입니다.";

    private final String cardNumber;
    private final String pin;

    public Card(String cardNumber, String pin) {
        validate(cardNumber, pin);

        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    private void validate(String cardNumber, String pin) {
        if(cardNumber.length() != VALID_CARD_LENGTH) {
            throw new IllegalArgumentException(INVALID_CARD_LENGTH);
        }

        if(pin.length() != VALID_PIN_LENGTH) {
            throw new IllegalArgumentException(INVALID_PIN_LENGTH);
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(pin, card.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, pin);
    }
}
