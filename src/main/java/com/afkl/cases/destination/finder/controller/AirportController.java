package com.afkl.cases.destination.finder.controller;

import com.afkl.cases.destination.finder.model.AirportLocation;
import com.afkl.cases.destination.finder.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;


    @GetMapping("/")
    public ResponseEntity<List<AirportLocation>> getAllLocations() {
        List<AirportLocation> locations = airportService.getAirports();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping(params = "term")
    public ResponseEntity<List<AirportLocation>> searchByTerm(@RequestParam("term") String term) {
        List<AirportLocation> locations = airportService.searchByTerm(term);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
}
