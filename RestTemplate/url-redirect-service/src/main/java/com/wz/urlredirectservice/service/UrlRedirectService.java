package com.wz.urlredirectservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UrlRedirectService {

    private final RestTemplate restTemplate;

    @Value("${external.service.url}")
    private String externalServiceUrl;

    public UrlRedirectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callOtherService(String code) {
        String url = externalServiceUrl + "/" + code;
        return restTemplate.getForObject(url, String.class);
    }
}
