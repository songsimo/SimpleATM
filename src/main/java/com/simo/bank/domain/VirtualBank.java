package com.simo.bank.domain;

import com.simo.account.domain.Account;
import com.simo.account.domain.VirtualBankAccount;
import com.simo.bank.exception.InvalidBankArgumentException;

import java.util.*;

public class VirtualBank implements BankApi {
    public static final String NO_SUCH_CARD_NUMBER = "해당 카드를 찾을 수 없습니다";
    public static final String PIN_CHECK_MESSAGE = "PIN 번호를 확인해주세요.";

    private List<Card> cardList;
    private Map<String, List<Account>> accountMap;

    public VirtualBank(List<Card> cardList, Map<String, List<Account>> accountMap) {
        this.cardList = cardList;
        this.accountMap = accountMap;
    }

    @Override
    public void validatePin(String cardNumber, String pin) {
        Card card = cardList.stream()
                .filter(o -> o.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElseThrow(() -> new InvalidBankArgumentException(NO_SUCH_CARD_NUMBER));

        if(!card.getPin().equals(pin)) {
            throw new InvalidBankArgumentException(PIN_CHECK_MESSAGE);
        }
    }

    @Override
    public List<Account> findAccountByCardNumber(String cardNumber) {
        return accountMap.getOrDefault(cardNumber, new ArrayList<>());
    }

    @Override
    public int getBalance(String cardNumber, String accountNumber) {
        List<Account> accountList = accountMap.get(cardNumber);

        if(accountList == null || accountList.isEmpty()) {
            return 0;
        }

        Optional<Account> account = accountList.stream()
                .filter(o -> o.getAccountNumber().equals(accountNumber))
                .findFirst();

        return account.map(Account::getBalance).orElse(0);
    }

    @Override
    public void deposit(String cardNumber, String accountNumber, int amount) {
        List<Account> accountList = accountMap.get(cardNumber);

            if(accountList != null && !accountList.isEmpty()) {
                Optional<Account> account = accountList.stream()
                        .filter(o -> o.getAccountNumber().equals(accountNumber))
                        .findFirst();

                account.ifPresent(value -> ((VirtualBankAccount) value).deposit(amount));
        }
    }

    @Override
    public void withdraw(String cardNumber, String accountNumber, int amount) {
        List<Account> accountList = accountMap.get(cardNumber);

        if(accountList != null && !accountList.isEmpty()) {
            Optional<Account> account = accountList.stream()
                    .filter(o -> o.getAccountNumber().equals(accountNumber))
                    .findFirst();

            account.ifPresent(value -> ((VirtualBankAccount) value).withdraw(amount));
        }
    }

    public static VirtualBank of(List<Card> cardList, Map<String, Map<String, Integer>> accoruntMap) {
        Map<String, List<Account>> accounts = new HashMap<>();

        for(String cardNumber: accoruntMap.keySet()) {
            accounts.put(cardNumber, VirtualBankAccount.of(accoruntMap.get(cardNumber)));
        }

        return new VirtualBank(cardList, accounts);
    }
}
