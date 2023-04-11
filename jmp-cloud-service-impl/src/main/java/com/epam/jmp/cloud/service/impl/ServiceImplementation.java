package com.epam.jmp.cloud.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;
import com.epam.jmp.service.api.SubscriptionException;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImplementation implements Service {

    private final Map<User, List<Subscription>> userSubscriptionsStorage = new HashMap<>();

    @Override
    public void subscribe(BankCard bankCard) {
        User user = bankCard.getUser();
        String bankCardNumber = bankCard.getNumber();
        Subscription subscription = new Subscription(bankCardNumber, LocalDate.now());

        userSubscriptionsStorage.computeIfAbsent(user,u -> new LinkedList<>()).add(subscription);

    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) throws SubscriptionException {
        return Optional.of(userSubscriptionsStorage.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(subscription -> subscription.bankCardNumber().equals(bankCardNumber)).findFirst().orElseThrow(() -> new SubscriptionException("Subscription by bank card was not found")));
    }

    @Override
    public List<Subscription> getAllSubscriptionByCondition(Predicate<Subscription> filter) {
        return userSubscriptionsStorage.values()
                .stream().flatMap(Collection::stream)
                .filter(filter).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userSubscriptionsStorage.keySet().stream().toList();
    }

    public static boolean isPayable(User user){
        LocalDate birthday = user.birthday();
        return birthday.isBefore(LocalDate.now().minusYears(18));
    }
}
