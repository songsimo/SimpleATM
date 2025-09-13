package com.simo.domain;

public abstract class Account {
    protected String accountNumber;
    protected int balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }
}
