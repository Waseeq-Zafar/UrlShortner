package com.wz.urlredirectservice.service;

import com.wz.grpc.RedirectRequest;
import com.wz.grpc.RedirectResponse;
import com.wz.grpc.RedirectServiceGrpc;
import com.wz.urlredirectservice.grpc.RedirectServiceGrpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class UrlRedirectService {

    // Commented out: REST-based logic
    // private final RestTemplate restTemplate;

    // @Value("${external.service.url}")
    // private String externalServiceUrl;

    // public UrlRedirectService(RestTemplate restTemplate) {
    //     this.restTemplate = restTemplate;
    // }

    private final RedirectServiceGrpcClient grpcClient;

    public UrlRedirectService(RedirectServiceGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public String callOtherService(String code) {
        return grpcClient.getOriginalUrl(code);
    }
}
