package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.config.OAuthConfig;
import com.afkl.cases.destination.finder.model.Fare;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FareServiceImplTest {

    @InjectMocks
    private FareServiceImpl fareService;

    @Mock
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Mock
    private OAuthConfig oAuthConfig;

    void should_calculate_fare_between_source_destination() {

        Fare expectedFare = new Fare();
        expectedFare.setAmount(1234);
        expectedFare.setOrigin("AMS");
        expectedFare.setDestination("BOM");

        ResponseEntity<Fare> responseEntity = new ResponseEntity<>(expectedFare, HttpStatus.OK);

        when(oAuthConfig.getFareUrl()).thenReturn("http://localhost:8080/fares/{origin}/{destination}");
        when(oAuth2RestTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(responseEntity);

        final Fare actualFare = fareService.calculateFare(expectedFare.getOrigin(), expectedFare.getDestination());

        assertEquals(expectedFare.getAmount(), actualFare.getAmount());
    }

}