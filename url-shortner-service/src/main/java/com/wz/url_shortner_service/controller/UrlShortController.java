package com.wz.url_shortner_service.controller;

import com.wz.url_shortner_service.service.UrlShortService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortController {

    private final UrlShortService urlShortService;

    public UrlShortController(UrlShortService urlShortService) {
        this.urlShortService = urlShortService;
    }

    // POST to /api/create with raw URL string in body
    @PostMapping(value = "/api/create", consumes = "text/plain")
    public ResponseEntity<String> createShortUrl(@RequestBody String originalUrl) {
        // Trim in case of any whitespace
        String trimmedUrl = originalUrl.trim();
        String shortUrl = urlShortService.createUrl(trimmedUrl);
        return ResponseEntity.ok(shortUrl);
    }

    // GET to /{shortCode} for redirecting
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {
        String originalUrl = urlShortService.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", originalUrl);
            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
