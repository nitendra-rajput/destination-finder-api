package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.model.Fare;

public interface FareService {
    Fare calculateFare(String origin, String destination);
}
