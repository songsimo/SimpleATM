package com.simo.bank.domain;

import com.simo.account.domain.Account;

import java.util.List;

public interface BankApi {
    void validatePin(String cardNumber, String pin);
    List<Account> findAccountByCardNumber(String cardNumber);
    int getBalance(String accountNumber);
    void deposit(String accountNumber, double amount);
    void withdraw(String accountNumber, double amount);
}
