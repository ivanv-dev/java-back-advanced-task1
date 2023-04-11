package com.epam.application;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.cloud.bank.impl.BankImplementation;
import com.epam.jmp.cloud.service.impl.ServiceImplementation;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;
import com.epam.jmp.service.api.SubscriptionException;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        User user = new User("John", "Doe", LocalDate.of(2000,1, 10));
        User secondUser = new User("Mark", "Doe", LocalDate.of(2009,1, 25));

        Bank bank = new BankImplementation();
        Service service = new ServiceImplementation();

        BankCard debitCard = bank.createBankCard(user, BankCardType.DEBIT);
        BankCard creditCard = bank.createBankCard(secondUser, BankCardType.CREDIT);

        service.subscribe(debitCard);
        service.subscribe(creditCard);

        Optional<Subscription> subscriptionByDebitCardNumber;
        try {
            subscriptionByDebitCardNumber = service.getSubscriptionByBankCardNumber(debitCard.getNumber());
            subscriptionByDebitCardNumber.ifPresent(System.out::println);
        } catch (SubscriptionException e) {
            e.printStackTrace();
        }
        double averageUserAge = service.getAverageUserAge();
        System.out.println("Is first user payable: " + ServiceImplementation.isPayable(user));
        System.out.println("Is second user payable: " + ServiceImplementation.isPayable(secondUser));
        System.out.println("Average user age: " + averageUserAge);
        System.out.println(service.getAllSubscriptionByCondition(s -> s.bankCardNumber().contains("0")).size());
    }
}