package com.example.springreactivemongodbaggregation.web.router;

import com.example.springreactivemongodbaggregation.web.handler.InvoiceHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class InvoiceRouter {
    @Bean
    public RouterFunction<ServerResponse> routeUserAccount(final InvoiceHandler invoiceHandler) {

        return route()
                .nest(path("/invoice/count"), builder ->
                        builder
                                .GET("/mongo-repository", invoiceHandler::reactiveMongoRepositoryAggregation)
                                .GET("/mongo-template", invoiceHandler::reactiveMongoTemplateAggregation)).build();
    }

}