package com.afkl.cases.destination.finder.service;

import com.afkl.cases.destination.finder.config.OAuthConfig;
import com.afkl.cases.destination.finder.exception.ApplicationException;
import com.afkl.cases.destination.finder.exception.BadRequestException;
import com.afkl.cases.destination.finder.exception.NotFoundException;
import com.afkl.cases.destination.finder.model.Fare;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {
    private final OAuth2RestTemplate oAuth2RestTemplate;
    private final OAuthConfig oAuthConfig;

    @Override
    public Fare calculateFare(String origin, String destination) {
        try {
            return oAuth2RestTemplate.getForEntity(oAuthConfig.getFareUrl(), Fare.class, origin, destination).getBody();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode()) {
                case BAD_REQUEST:
                    throw new BadRequestException("Bad request, there are no flights operated between given origin and destination");
                case NOT_FOUND:
                    throw new NotFoundException("No flight found between given origin and destination");
                default:
                    throw new ApplicationException("Something went wrong");
            }
        }
    }
}
