package com.afkl.cases.destination.finder.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
@Data
public class OAuthConfig {

    @Value("${oauth2.client-id}")
    private String clientId;

    @Value("${oauth2.client-secret-encrypted}")
    private String clientSecretEnc;

    @Value("${oauth2.token-url}")
    private String tokenUrl;

    @Value("${mock.fare-url}")
    private String fareUrl;

    @Value("${mock.airports-url}")
    private String airportsUrl;

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecretEnc);
        resourceDetails.setAccessTokenUri(tokenUrl);
        return new OAuth2RestTemplate(resourceDetails);
    }
}

