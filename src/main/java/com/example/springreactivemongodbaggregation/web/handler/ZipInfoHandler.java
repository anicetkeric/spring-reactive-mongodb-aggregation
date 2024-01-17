package com.example.springreactivemongodbaggregation.web.handler;

import com.example.springreactivemongodbaggregation.service.ZipInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ZipInfoHandler {

    private final ZipInfoService zipInfoService;

    public Mono<ServerResponse> citiesState(ServerRequest serverRequest) {
        return zipInfoService.getCitiesByState()
                .collectList()
                .flatMap(results ->
                        ServerResponse.status(HttpStatus.OK)
                                .bodyValue(results));
    }
    public Mono<ServerResponse> getLargestAndSmallestCitiesByState(ServerRequest serverRequest) {
        return zipInfoService.getLargestAndSmallestCitiesByState()
                .collectList()
                .flatMap(results ->
                        ServerResponse.status(HttpStatus.OK)
                                .bodyValue(results));
    }

}
