package com.simo.domain;

import com.simo.exception.InvalidAccountArgumentException;
import com.simo.exception.BalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VirtualBankAccountTest {
    private final int INVALID_AMOUNT = -1;
    private final int ZERO_AMOUNT = 0;

    private final String ACCOUNT_NUMBER = "123-4889-9289-91";
    private final int ONE_THOUSAND = 1_000;

    private VirtualBankAccount account;

    @BeforeEach
    void setUp() {
        account = new VirtualBankAccount(ACCOUNT_NUMBER, ONE_THOUSAND);
    }

    @Test
    @DisplayName("입금 금액이 0 또는 음수일 경우 Exception 발생")
    void invalidDeposit() {
        assertThatThrownBy(() -> account.deposit(INVALID_AMOUNT)).isInstanceOf(InvalidAccountArgumentException.class);
        assertThatThrownBy(() -> account.deposit(ZERO_AMOUNT)).isInstanceOf(InvalidAccountArgumentException.class);
    }

    @Test
    @DisplayName("계좌에 금액을 입금한다.")
    void addAmount() {
        int balanceBeforeDeposit = account.getBalance();

        account.deposit(ONE_THOUSAND);

        int balanceAfterDeposit = account.getBalance();

        assertThat(balanceAfterDeposit).isEqualTo(balanceBeforeDeposit + ONE_THOUSAND);
    }

    @Test
    @DisplayName("잔액이 부족하거나 출금 금액이 0 또는 음수일 경우 Exception 발생")
    void invalidWithdraw() {
        account.withdraw(ONE_THOUSAND);

        assertThatThrownBy(() -> account.withdraw(INVALID_AMOUNT)).isInstanceOf(InvalidAccountArgumentException.class);
        assertThatThrownBy(() -> account.withdraw(ZERO_AMOUNT)).isInstanceOf(InvalidAccountArgumentException.class);
        assertThatThrownBy(() -> account.withdraw(ONE_THOUSAND)).isInstanceOf(BalanceException.class);       // 잔액 부족
    }

    @Test
    @DisplayName("계좌에서 돈을 출금한다.")
    void withdrawAmount() {
        int balanceBeforeWithdraw = account.getBalance();

        account.withdraw(ONE_THOUSAND);

        int balanceAfterWithdraw = account.getBalance();

        assertThat(balanceAfterWithdraw).isEqualTo(balanceBeforeWithdraw - ONE_THOUSAND);
    }
}