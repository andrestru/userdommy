package com.example.dummy.infrastructure.driver_adapters.adapters;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DummyAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> loginDummy(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of("username", username, "password", password);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                "https://dummyjson.com/auth/login",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public ResponseEntity<String> getUserDummy(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "https://dummyjson.com/auth/me",
                HttpMethod.GET,
                request,
                String.class
        );
    }

}
