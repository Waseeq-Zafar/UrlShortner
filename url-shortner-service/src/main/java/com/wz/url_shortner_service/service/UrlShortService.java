package com.wz.url_shortner_service.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShortService {

    private final HashMap<String, String> urlShortMap = new HashMap<>();
    private static final long MAX_COUNTER = (long) Math.pow(62, 6) - 1;
    private final AtomicLong idCounter = new AtomicLong(1);
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SHORT_CODE_LENGTH = 6;

    public String createUrl(String url) {
        long id = idCounter.getAndUpdate(current -> (current < MAX_COUNTER) ? current + 1 : 1);
        String shortCode = encodeBase62(id);
        urlShortMap.put(shortCode , url);
        return "http://localhost:8080/" + shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        return urlShortMap.get(shortCode);
    }

    private String encodeBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            int remainder = (int) (value % 62);
            sb.append(BASE62.charAt(remainder));
            value /= 62;
        }
        return padLeft(sb.reverse().toString(), SHORT_CODE_LENGTH);
    }

    private String padLeft(String str, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length(); i < length; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
}
