package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.model.AirportLocation;
import com.afkl.cases.destination.finder.model.Location;

import java.util.List;

public interface AirportService {
    List<AirportLocation> getAirports();
    List<AirportLocation> searchByTerm(String term);
}
