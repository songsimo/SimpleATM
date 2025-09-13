package com.simo.account.domain;

import com.simo.account.exception.InvalidAccountArgumentException;

public class Account {
    private static final String INVALID_DEPOSIT_AMOUNT = "입금하려는 금액이 유효하지 않습니다.";

    private String accountNumber;
    private int balance;

    public Account(String accountNumber, int balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    //
    public synchronized void deposit(int amount) {
        if(amount <= 0) {
            throw new InvalidAccountArgumentException(INVALID_DEPOSIT_AMOUNT);
        }

        balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}
