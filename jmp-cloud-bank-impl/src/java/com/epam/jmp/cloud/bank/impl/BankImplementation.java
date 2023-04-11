package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImplementation implements Bank {

    private final Map<BankCardType, BiFunction<String, User, BankCard>> creators;

    public BankImplementation() {
        creators = new HashMap<>();
        creators.put(BankCardType.CREDIT, CreditBankCard::new);
        creators.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        String bankCardNumber = UUID.randomUUID().toString();
        return creators.getOrDefault(bankCardType, this::throwIfTypeUnknown).apply(bankCardNumber, user);
    }

    private BankCard throwIfTypeUnknown(String c, User u){
        throw new IllegalArgumentException("Type cannot be null/empty");
 }
}
