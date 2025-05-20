package com.wz.urlredirectservice.controller;


import com.wz.urlredirectservice.service.UrlRedirectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlRedirectController {
    private final UrlRedirectService urlRedirectService;

    public UrlRedirectController(UrlRedirectService urlRedirectService) {
        this.urlRedirectService = urlRedirectService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<String> getUrl(@PathVariable String code) {
        String longUrl = urlRedirectService.callOtherService(code);
        if (longUrl != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", longUrl);
            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
