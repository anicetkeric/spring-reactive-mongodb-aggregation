package com.example.springreactivemongodbaggregation.web.router;

import com.example.springreactivemongodbaggregation.web.handler.ZipInfoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@RequiredArgsConstructor
public class ZipInfoRouter {

    @Bean
    public RouterFunction<ServerResponse> routeZipInfo(final ZipInfoHandler zipInfoHandler) {

        return route()
                .nest(path("/zip-info"), builder ->
                        builder
                                .GET("/state-cities", zipInfoHandler::citiesState)
                                .GET("/biggest-smallest-city", zipInfoHandler::getLargestAndSmallestCitiesByState)
                ).build();
    }
}
