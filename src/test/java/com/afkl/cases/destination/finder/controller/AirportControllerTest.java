package com.afkl.cases.destination.finder.controller;

import com.afkl.cases.destination.finder.exception.NotFoundException;
import com.afkl.cases.destination.finder.model.AirportLocation;
import com.afkl.cases.destination.finder.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirportController.class)
@OverrideAutoConfiguration(enabled = true)
class AirportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    List<AirportLocation> locations;

    @BeforeEach
    void setUp() {
        locations = Collections.singletonList(
                AirportLocation.builder()
                        .code("AMS")
                        .name("Schiphol")
                        .description("Amsterdam - Schiphol (AMS), Netherlands")
                        .build());
    }

    @Test
    void should_return_all_airports() throws Exception {

        when(airportService.getAirports()).thenReturn(locations);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/airports/")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void should_return_all_airports_by_term() throws Exception {
        when(airportService.getAirports()).thenReturn(locations);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/airports/?term=amst")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void should_return_not_found_when_invalid_airport_is_passed() throws Exception {
        when(airportService.getAirports()).thenThrow(new NotFoundException("No airport found"));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/airports/?term=amst1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().is4xxClientError());
    }
}