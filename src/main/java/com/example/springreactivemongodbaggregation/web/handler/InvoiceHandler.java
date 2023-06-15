package com.example.springreactivemongodbaggregation.web.handler;


import com.example.springreactivemongodbaggregation.exception.ValidatorException;
import com.example.springreactivemongodbaggregation.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class InvoiceHandler {

    private final InvoiceService invoiceService;


    public Mono<ServerResponse> reactiveMongoRepositoryAggregation(ServerRequest serverRequest) {

        LocalDate dateFrom = serverRequest.queryParam("startDate").map(this::validateInputDate).orElse(null);
        LocalDate dateTo = serverRequest.queryParam("endDate").map(this::validateInputDate).orElse(null);

        if (dateFrom == null || dateTo == null){
            throw new ValidatorException("The date parameters are not valid, please verify");
        }

        return invoiceService.repositoryAggregation(dateFrom, dateTo)
                .flatMap(invoiceCountSummary ->
                        ServerResponse.status(HttpStatus.OK)
                                .bodyValue(invoiceCountSummary));
    }


    public Mono<ServerResponse> reactiveMongoTemplateAggregation(ServerRequest serverRequest) {

        LocalDate dateFrom = serverRequest.queryParam("startDate").map(this::validateInputDate).orElse(null);
        LocalDate dateTo = serverRequest.queryParam("endDate").map(this::validateInputDate).orElse(null);

        if (dateFrom == null || dateTo == null){
            throw new ValidatorException("The date parameters are not valid, please verify");
        }

        return invoiceService.mongoTemplateAggregation(dateFrom, dateTo)
                .flatMap(invoiceCountSummary ->
                        ServerResponse.status(HttpStatus.OK)
                                .bodyValue(invoiceCountSummary));
    }


    private LocalDate validateInputDate(String inputDate){
        try {
            return LocalDate.parse(inputDate);
        }catch (DateTimeParseException e){
            return null;
        }
    }
}