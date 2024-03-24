package com.fiap.ms_estoque.adapters.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidateTokenOutService {
    @Value("${api-comunicationauth.host}")
    private String apiHostce;
    private final RestTemplate restTemplate=new RestTemplate();

    public Boolean tokenIsActive(String token) {
        String url = apiHostce + "?jwt=" + token;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
        return response.getBody();
    }
}
