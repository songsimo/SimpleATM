package com.simo.service;

import com.simo.domain.BankApi;

public class AtmService {
    private static final String NOT_AUTH_MESSAGE = "먼저 인증을 진행해주세요.";
    private static final String CHECK_VALUE_MESSAGE = "카드 번호와 계좌번호 정보를 다시 확인해주세요.";

    private final BankApi bankApi;

    private String currentCardNumber;
    private String currentAccountNumber;

    public AtmService(BankApi bankApi) {
        this.bankApi = bankApi;
    }

    public void insertCardAndPin(String cardNumber, String pin) {
        bankApi.validatePin(cardNumber, pin);

        this.currentCardNumber = cardNumber;
        this.currentAccountNumber = cardNumber;
    }

    public int getBalance() {
        if(this.currentCardNumber == null || this.currentAccountNumber == null) {
            throw new RuntimeException(NOT_AUTH_MESSAGE);
        }

        return bankApi.getBalance(this.currentCardNumber, this.currentAccountNumber);
    }

    public void deposit(int amount) {
        if(this.currentCardNumber == null || this.currentAccountNumber == null) {
            throw new RuntimeException(CHECK_VALUE_MESSAGE);
        }

        bankApi.deposit(this.currentCardNumber, this.currentAccountNumber, amount);
    }

    public void withdraw(int amount) {
        if(this.currentCardNumber == null || this.currentAccountNumber == null) {
            throw new RuntimeException(CHECK_VALUE_MESSAGE);
        }

        bankApi.withdraw(this.currentCardNumber, this.currentAccountNumber, amount);
    }
}
