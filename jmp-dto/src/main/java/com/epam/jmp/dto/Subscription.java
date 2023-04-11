package com.epam.jmp.dto;

import java.time.LocalDate;

public record Subscription(String bankCardNumber, LocalDate startDate) {
    @Override
    public String toString() {
        return "Subscription{" +
                "bankCardNumber='" + bankCardNumber + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
