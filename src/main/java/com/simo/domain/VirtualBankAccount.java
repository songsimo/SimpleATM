package com.simo.domain;

import com.simo.exception.InvalidAccountArgumentException;
import com.simo.exception.BalanceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VirtualBankAccount extends Account implements FinancialTransactions {
    private static final String INVALID_DEPOSIT_AMOUNT = "입금하려는 금액이 유효하지 않습니다.";
    private static final String INVALID_WITHDRAW_AMOUNT = "출금하려는 금액이 유효하지 않습니다.";
    private static final String EMPTY_BALANCE = "잔액이 부족합니다.";

    public VirtualBankAccount(String accountNumber, int balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @Override
    public synchronized void deposit(int amount) {
        if(amount <= 0) {
            throw new InvalidAccountArgumentException(INVALID_DEPOSIT_AMOUNT);
        }

        this.balance += amount;
    }

    @Override
    public synchronized void withdraw(int amount) {
        if(amount <= 0) {
            throw new InvalidAccountArgumentException(INVALID_WITHDRAW_AMOUNT);
        }

        if(this.balance < amount) {
            throw new BalanceException(EMPTY_BALANCE);
        }

        this.balance -= amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public static List<Account> of(Map<String, Integer> accountMap) {
        List<Account> result = new ArrayList<>();

        for(String key: accountMap.keySet()) {
            result.add(new VirtualBankAccount(key, accountMap.get(key)));
        }

        return result;
    }
}
