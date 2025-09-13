package com.simo.bank.domain;

import com.simo.account.domain.Account;

import java.util.List;

public interface BankApi {
    void validatePin(String cardNumber, String pin);
    List<Account> findAccountByCardNumber(String cardNumber);
    int getBalance(String cardNumber, String accountNumber);
    void deposit(String cardNumber, String accountNumber, int amount);
    void withdraw(String cardNumber, String accountNumber, int amount);
}
