package com.simo.account.domain;

import com.simo.account.exception.InvalidAccountArgumentException;
import com.simo.bank.exception.BalanceException;

public class Account {
    private static final String INVALID_DEPOSIT_AMOUNT = "입금하려는 금액이 유효하지 않습니다.";
    private static final String INVALID_WITHDRAW_AMOUNT = "출금하려는 금액이 유효하지 않습니다.";
    private static final String EMPTY_BALANCE = "잔액이 부족합니다.";

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

        this.balance += amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public synchronized void withdraw(int amount) {
        if(amount <= 0) {
            throw new InvalidAccountArgumentException(INVALID_WITHDRAW_AMOUNT);
        }

        if(this.balance < amount) {
            throw new BalanceException(EMPTY_BALANCE);
        }

        this.balance -= amount;
    }
}
