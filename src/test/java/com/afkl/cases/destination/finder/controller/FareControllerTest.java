package com.afkl.cases.destination.finder.controller;

import com.afkl.cases.destination.finder.exception.BadRequestException;
import com.afkl.cases.destination.finder.model.Fare;
import com.afkl.cases.destination.finder.service.FareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FareController.class)
@OverrideAutoConfiguration(enabled = true)
class FareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FareService fareService;

    @Test
    void should_estimate_fare_for_a_given_source_destination() throws Exception {
        Fare fare = new Fare();
        fare.setOrigin("AMS");
        fare.setDestination("BOM");
        fare.setAmount(1234.00);

        when(fareService.calculateFare(anyString(), anyString())).thenReturn(fare);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/fares/AMS/BOM")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void should_return_bad_request_when_no_connectivity_between_source_and_destination() throws Exception {
        when(fareService.calculateFare(anyString(), anyString()))
                .thenThrow(new BadRequestException("Bad request, there are no flights operated between given origin and destination"));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/fares/AMS1/BOM")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}