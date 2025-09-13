package com.simo.domain;

import com.simo.exception.InvalidBankArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class VirtualBankTest {
    private final String INVALID_CARD_NUMBER = "9999999999999999";
    private final String INVALID_PIN_NUMBER = "9999";

    private final String VALID_CARD_NUMBER_ONE = "1234567891011111";
    private final String VALID_CARD_NUMBER_TWO = "4242567891011123";
    private final String VALID_PIN_NUMBER = "1234";

    private final String ACCOUNT_NUMBER_ONE = "123-4889-9289-91";
    private final String ACCOUNT_NUMBER_TWO = "101-4889-9289-91";
    private final String ACCOUNT_NUMBER_THREE = "533-4111-9289-91";

    private final int ONE_THOUSAND = 1_000;
    private final int ONE_HUNDRED = 100;

    private final Map<String, Integer> ACCOUNT_INFO_BY_CARD_ONE = Map.of(
            ACCOUNT_NUMBER_ONE, ONE_THOUSAND
    );

    private final Map<String, Integer> ACCOUNT_INFO_BY_CARD_TWO = Map.of(
            ACCOUNT_NUMBER_TWO, ONE_THOUSAND * 2,
            ACCOUNT_NUMBER_THREE, ONE_THOUSAND * 3
    );

    private VirtualBank bank;

    @BeforeEach
    void setUp() {
        bank = VirtualBank.of(List.of(
                new Card(VALID_CARD_NUMBER_ONE, VALID_PIN_NUMBER),
                new Card(VALID_CARD_NUMBER_TWO, VALID_PIN_NUMBER)
        ), Map.of(
                VALID_CARD_NUMBER_ONE, ACCOUNT_INFO_BY_CARD_ONE,
                VALID_CARD_NUMBER_TWO, ACCOUNT_INFO_BY_CARD_TWO
        ));
    }

    @Test
    @DisplayName("은행에 해당 카드번호나 PIN번호가 맞지 않으면 Exception 발생")
    void isValidCardAndPinNumber() {
        assertThatThrownBy(() -> bank.validatePin(VALID_CARD_NUMBER_ONE, INVALID_PIN_NUMBER)).isInstanceOf(InvalidBankArgumentException.class);
        assertThatThrownBy(() -> bank.validatePin(INVALID_CARD_NUMBER, VALID_PIN_NUMBER)).isInstanceOf(InvalidBankArgumentException.class);
    }

    @Test
    @DisplayName("카드번호와 연결된 계좌 목록을 조회")
    void searchAccountList() {
        List<Account> result = bank.findAccountByCardNumber(VALID_CARD_NUMBER_ONE);

        Account account = result.getFirst();

        assertThat(account.getAccountNumber()).isEqualTo(ACCOUNT_NUMBER_ONE);
        assertThat(account.getBalance()).isEqualTo(ONE_THOUSAND);
    }

    @Test
    @DisplayName("계좌의 잔액을 조회한다. 유효하지 않은 계좌를 조회할 경우 잔액 0을 반환")
    void getBalance() {
        Account account = bank.findAccountByCardNumber(VALID_CARD_NUMBER_ONE).getFirst();
        int balance = bank.getBalance(VALID_CARD_NUMBER_ONE, account.getAccountNumber());
        int balanceOfInvalidCard = bank.getBalance(INVALID_CARD_NUMBER, account.getAccountNumber());
        int balanceOfInvalidAccount = bank.getBalance(VALID_CARD_NUMBER_ONE, ACCOUNT_NUMBER_THREE);

        assertThat(balance).isEqualTo(ONE_THOUSAND);
        assertThat(balanceOfInvalidCard).isEqualTo(0);
        assertThat(balanceOfInvalidAccount).isEqualTo(0);
    }

    @Test
    @DisplayName("계좌에 금액을 입금한다.")
    void depositAmount() {
        Account accountBeforeDeposit = bank.findAccountByCardNumber(VALID_CARD_NUMBER_ONE).getFirst();
        int balanceBeforeDeposit = bank.getBalance(VALID_CARD_NUMBER_ONE, accountBeforeDeposit.getAccountNumber());
        int expectedBalance = balanceBeforeDeposit + ONE_HUNDRED;

        bank.deposit(VALID_CARD_NUMBER_ONE, accountBeforeDeposit.getAccountNumber(), ONE_HUNDRED);

        int balanceAfterDeposit = bank.getBalance(VALID_CARD_NUMBER_ONE, accountBeforeDeposit.getAccountNumber());

        assertThat(balanceAfterDeposit).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("계좌에 금액을 출금한다.")
    void withDrawAmount() {
        Account accountBeforeWithDraw = bank.findAccountByCardNumber(VALID_CARD_NUMBER_ONE).getFirst();
        int balanceBeforeWithdraw = bank.getBalance(VALID_CARD_NUMBER_ONE, accountBeforeWithDraw.getAccountNumber());
        int expectedBalance = balanceBeforeWithdraw - ONE_HUNDRED;

        bank.withdraw(VALID_CARD_NUMBER_ONE, accountBeforeWithDraw.getAccountNumber(), ONE_HUNDRED);

        int balanceAfterDeposit = bank.getBalance(VALID_CARD_NUMBER_ONE, accountBeforeWithDraw.getAccountNumber());

        assertThat(balanceAfterDeposit).isEqualTo(expectedBalance);
    }
}