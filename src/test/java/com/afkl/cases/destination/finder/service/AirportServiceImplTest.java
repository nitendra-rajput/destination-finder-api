package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.config.OAuthConfig;
import com.afkl.cases.destination.finder.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {

    @InjectMocks
    private AirportServiceImpl airportService;

    @Mock
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Mock
    private OAuthConfig oAuthConfig;

    LocationWrapper locationWrapper;

    @BeforeEach
    void setUp() {
        locationWrapper = new LocationWrapper();
        locationWrapper.setPage(new Page());

        Location location = new Location();
        location.setCode("BOM");

        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Embedded embedded = new Embedded();
        embedded.setLocations(locations);

        locationWrapper.set_embedded(embedded);
    }

    @Test
    void should_return_airport_list() {
        ResponseEntity<LocationWrapper> responseEntity = new ResponseEntity<>(locationWrapper, HttpStatus.OK);

        when(oAuth2RestTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(responseEntity);
        when(oAuthConfig.getAirportsUrl()).thenReturn("http://localhost:8080/airports");

        final List<AirportLocation> airports = airportService.getAirports();

        assertEquals(1, airports.size());
    }
}