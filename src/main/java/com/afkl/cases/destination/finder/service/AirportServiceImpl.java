package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.config.OAuthConfig;
import com.afkl.cases.destination.finder.exception.ApplicationException;
import com.afkl.cases.destination.finder.exception.BadRequestException;
import com.afkl.cases.destination.finder.exception.NotFoundException;
import com.afkl.cases.destination.finder.model.AirportLocation;
import com.afkl.cases.destination.finder.model.Location;
import com.afkl.cases.destination.finder.model.LocationWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final OAuth2RestTemplate oAuth2RestTemplate;
    private final OAuthConfig oAuthConfig;

    @Override
    public List<AirportLocation> getAirports() {
        final String url = oAuthConfig.getAirportsUrl();
        return getLocations(url);
    }

    @Override
    public List<AirportLocation> searchByTerm(String term) {
        String url = UriComponentsBuilder
                .fromUriString(oAuthConfig.getAirportsUrl())
                .queryParam("term", term).build().toUriString();
        return getLocations(url);
    }

    private List<AirportLocation> getLocations(String url) {
        try {
            final ResponseEntity<LocationWrapper> responseEntity = oAuth2RestTemplate.getForEntity(url, LocationWrapper.class);

            LocationWrapper locationWrapper = responseEntity.getBody();
            assert locationWrapper != null;

            final List<Location> locations = locationWrapper.get_embedded().getLocations();

            return locations.stream()
                    .map(this::createAirportLocation)
                    .collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode()) {
                case BAD_REQUEST:
                    throw new BadRequestException("Bad request");
                case NOT_FOUND:
                    throw new NotFoundException("No airport found");
                default:
                    throw new ApplicationException("Something went wrong");
            }
        }
    }

    private AirportLocation createAirportLocation(Location location) {
        return AirportLocation.builder()
                .code(location.getCode())
                .name(location.getName())
                .description(location.getDescription())
                .build();
    }
}
