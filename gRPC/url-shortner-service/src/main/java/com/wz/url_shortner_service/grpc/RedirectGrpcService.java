package com.wz.url_shortner_service.grpc;

import com.wz.grpc.RedirectRequest;
import com.wz.grpc.RedirectResponse;
import com.wz.grpc.RedirectServiceGrpc;
import com.wz.url_shortner_service.service.UrlShortService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RedirectGrpcService extends RedirectServiceGrpc.RedirectServiceImplBase {

    private final UrlShortService urlShortService;

    public RedirectGrpcService(UrlShortService urlShortService) {
        this.urlShortService = urlShortService;
    }

    @Override
    public void getOriginalUrl(RedirectRequest request, StreamObserver<RedirectResponse> responseObserver) {
        String originalUrl = urlShortService.getOriginalUrl(request.getShortCode());
        boolean found = originalUrl != null;

        RedirectResponse response = RedirectResponse.newBuilder()
                .setOriginalUrl(found ? originalUrl : "")
                .setFound(found)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
