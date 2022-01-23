package com.ddd.tooda.domain.auth.oauth.apple;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class AppleClient {
    private static String baseUrl = "https://appleid.apple.com/auth";

    private final RestTemplate restTemplate;

    public AppleClient() {
        restTemplate =  new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    public ApplePublicKeyResponse getAppleAuthPublicKey() {
        return restTemplate.getForObject("/keys",ApplePublicKeyResponse.class);
    }

}
