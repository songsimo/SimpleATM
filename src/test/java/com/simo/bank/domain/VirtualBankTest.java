package com.simo.bank.domain;

import com.simo.bank.exception.InvalidBankArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class VirtualBankTest {
    private final String INVALID_CARD_NUMBER = "9999999999999999";
    private final String INVALID_CARD_LENGTH = "123456789";
    private final String INVALID_PIN_NUMBER = "9999";
    private final String INVALID_PIN_LENGTH = "999999";

    private VirtualBank bank;

    @BeforeEach
    void setUp() {
        bank = new VirtualBank();
    }

    @Test
    @DisplayName("Card 번호가 유효하면 true 아니면 false 반환")
    void isValidCardNumber() {
        assertAll(
                () -> assertThat(bank.isValidCard(INVALID_CARD_NUMBER)).isFalse(),
                () -> assertThat(bank.isValidCard(INVALID_CARD_LENGTH)).isFalse(),
                () -> assertThat(bank.isValidCard(VirtualBank.VALID_CARD_NUMBER)).isTrue()
        );
    }

    @Test
    @DisplayName("Pin 번호가 유효하면 true 아니면 false 반환")
    void isValidPinNumber() {
        assertAll(
                () -> assertThat(bank.isValidPin(INVALID_PIN_NUMBER)).isFalse(),
                () -> assertThat(bank.isValidPin(INVALID_PIN_LENGTH)).isFalse(),
                () -> assertThat(bank.isValidPin(VirtualBank.VALID_PIN_NUMBER)).isTrue()
        );
    }

    @Test
    @DisplayName("Card 혹은 PIN 번호 개수가 맞지 않거나 유효하지 않을 경우 Exception 발생")
    void validate() {
        bank.validate(VirtualBank.VALID_CARD_NUMBER, VirtualBank.VALID_PIN_NUMBER);

        assertThatThrownBy(() -> bank.validate(INVALID_CARD_NUMBER, VirtualBank.VALID_PIN_NUMBER)).isInstanceOf(InvalidBankArgumentException.class);
        assertThatThrownBy(() -> bank.validate(INVALID_CARD_LENGTH, VirtualBank.VALID_PIN_NUMBER)).isInstanceOf(InvalidBankArgumentException.class);
        assertThatThrownBy(() -> bank.validate(VirtualBank.VALID_CARD_NUMBER, INVALID_PIN_NUMBER)).isInstanceOf(InvalidBankArgumentException.class);
        assertThatThrownBy(() -> bank.validate(VirtualBank.VALID_CARD_NUMBER, INVALID_PIN_LENGTH)).isInstanceOf(InvalidBankArgumentException.class);
    }
}