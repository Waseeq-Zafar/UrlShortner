package com.wz.url_shortner_service.controller;

import com.wz.url_shortner_service.service.UrlShortService;
import org.springframework.http.ResponseEntity;
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
        String trimmedUrl = originalUrl.trim();
        String shortUrl = urlShortService.createUrl(trimmedUrl);
        return ResponseEntity.ok(shortUrl);
    }

    // Commented out - redirect logic will be replaced by gRPC call
    /*
    @GetMapping("/{shortCode}")
    public ResponseEntity<String> redirectToOriginal(@PathVariable String shortCode) {
        String originalUrl = urlShortService.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */
}
