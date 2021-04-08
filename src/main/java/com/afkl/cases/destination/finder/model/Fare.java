package com.afkl.cases.destination.finder.model;

import com.afkl.cases.destination.finder.enums.Currency;
import lombok.Data;

@Data
public class Fare {
    private double amount;
    private Currency currency;
    private String origin;
    private String destination;
}
