package com.wz.urlredirectservice.grpc;

import com.wz.grpc.RedirectRequest;
import com.wz.grpc.RedirectResponse;
import com.wz.grpc.RedirectServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedirectServiceGrpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RedirectServiceGrpcClient.class);

    private final ManagedChannel channel;
    private final RedirectServiceGrpc.RedirectServiceBlockingStub blockingStub;

    public RedirectServiceGrpcClient(@Value("${grpc.client.urlShortener.address}") String url) {
        logger.info("Initializing gRPC client with url: {}", url);
        try {
            // Parse the URL to extract host and port, ignoring the "static://" prefix
            String cleanedUrl = url.replace("static://", "");
            String[] parts = cleanedUrl.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid grpc client URL format. Expected host:port");
            }
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);

            this.channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            this.blockingStub = RedirectServiceGrpc.newBlockingStub(channel);
            logger.info("gRPC channel and stub initialized successfully at {}:{}", host, port);
        } catch (Exception e) {
            logger.error("Failed to initialize RedirectServiceGrpcClient with url {}: {}", url, e.getMessage(), e);
            throw new RuntimeException("Failed to initialize RedirectServiceGrpcClient", e);
        }
    }

    public String getOriginalUrl(String shortCode) {
        RedirectRequest request = RedirectRequest.newBuilder()
                .setShortCode(shortCode)
                .build();

        try {
            logger.info("Sending gRPC request for shortCode: {}", shortCode);
            RedirectResponse response = blockingStub.getOriginalUrl(request);
            logger.info("Received gRPC response: found={}, originalUrl={}", response.getFound(), response.getOriginalUrl());
            return response.getFound() ? response.getOriginalUrl() : null;
        } catch (Exception e) {
            logger.error("gRPC call failed for shortCode {}: {}", shortCode, e.getMessage(), e);
            return null;
        }
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Shutting down gRPC channel");
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            try {
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.warn("gRPC channel did not shut down in time, forcing shutdown");
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                logger.warn("gRPC channel shutdown interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
