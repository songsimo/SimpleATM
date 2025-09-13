package com.simo.bank.domain;

import com.simo.account.domain.Account;
import com.simo.account.domain.VirtualBankAccount;
import com.simo.bank.exception.InvalidBankArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualBank implements BankApi {
    public static final int VALID_CARD_LENGTH = 16;
    public static final int VALID_PIN_LENGTH = 4;

    public static final String NO_SUCH_CARD_NUMBER = "해당 카드를 찾을 수 없습니다";

    public static final String CARD_CHECK_MESSAGE = "Card 번호를 확인해주세요.";
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
    public int getBalance(String accountNumber) {
        return 0;
    }

    @Override
    public void deposit(String accountNumber, double amount) {

    }

    @Override
    public void withdraw(String accountNumber, double amount) {

    }

    public static VirtualBank of(List<Card> cardList, Map<String, Map<String, Integer>> accoruntMap) {
        Map<String, List<Account>> accounts = new HashMap<>();

        for(String cardNumber: accoruntMap.keySet()) {
            accounts.put(cardNumber, VirtualBankAccount.of(accoruntMap.get(cardNumber)));
        }

        return new VirtualBank(cardList, accounts);
    }
}
