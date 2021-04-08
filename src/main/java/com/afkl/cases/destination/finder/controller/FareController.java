package com.afkl.cases.destination.finder.controller;

import com.afkl.cases.destination.finder.model.Fare;
import com.afkl.cases.destination.finder.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FareController {
    private final FareService fareService;

    @GetMapping(value = "/fares/{origin}/{destination}")
    public ResponseEntity<Fare> calculateFare(@PathVariable("origin") String origin,
                                              @PathVariable("destination") String destination) {
        Fare fare = fareService.calculateFare(origin, destination);
        return new ResponseEntity<>(fare, HttpStatus.OK);
    }
}
