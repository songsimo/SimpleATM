package com.simo.service;

import com.simo.domain.Account;
import com.simo.domain.BankApi;
import com.simo.domain.VirtualBankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtmServiceTest {
    private final String VALID_CARD = "1234567891231234";
    private final String VALID_PIN = "1234";
    private final String VALID_ACCOUNT = "123-4889-9289-91";
    private final int ONE_THOUSAND = 1_000;

    private final List<Account> VALID_ACCOUNT_LIST = List.of(
            new VirtualBankAccount(VALID_ACCOUNT, ONE_THOUSAND)
    );

    @Mock
    private BankApi bankApi;

    private AtmService atmService;

    @BeforeEach
    void setUp() {
        atmService = new AtmService(bankApi);
    }

    @Test
    @DisplayName("정상적으로 인증을 하였을 경우 입금 성공")
    void succeedDeposit() {
        doNothing().when(bankApi).validatePin(VALID_CARD, VALID_PIN);
        atmService.insertCardAndPin(VALID_CARD, VALID_PIN);

        atmService.deposit(VALID_ACCOUNT_LIST.getFirst().getAccountNumber(), ONE_THOUSAND);

        verify(bankApi, times(1)).deposit(VALID_CARD, VALID_ACCOUNT, ONE_THOUSAND);
    }

    @Test
    @DisplayName("정상적으로 인증을 하였을 경우 인출 성공")
    void succeedWithdraw() {
        doNothing().when(bankApi).validatePin(VALID_CARD, VALID_PIN);
        atmService.insertCardAndPin(VALID_CARD, VALID_PIN);

        atmService.withdraw(VALID_ACCOUNT_LIST.getFirst().getAccountNumber(), ONE_THOUSAND);

        verify(bankApi, times(1)).withdraw(VALID_CARD, VALID_ACCOUNT, ONE_THOUSAND);
    }
}