package com.epam.jmp.service.api;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) throws SubscriptionException;

    List<Subscription> getAllSubscriptionByCondition(Predicate<Subscription> filter);

    List<User> getAllUsers();

    default double getAverageUserAge(){
       return getAllUsers().stream()
               .mapToInt(u -> Period.between(u.birthday(), LocalDate.now())
                       .getYears()).average().orElse(0);
    }

}
