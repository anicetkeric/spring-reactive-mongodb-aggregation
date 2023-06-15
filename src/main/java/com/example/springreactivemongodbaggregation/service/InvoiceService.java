package com.example.springreactivemongodbaggregation.service;


import com.example.springreactivemongodbaggregation.document.Invoice;
import com.example.springreactivemongodbaggregation.document.InvoiceStatusEnum;
import com.example.springreactivemongodbaggregation.model.InvoiceCountSummary;
import com.example.springreactivemongodbaggregation.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<InvoiceCountSummary> repositoryAggregation(LocalDate dateFrom, LocalDate dateTo) {
        return invoiceRepository.findInvoiceSummary(dateFrom, dateTo)
                .switchIfEmpty(Mono.defer(() -> Mono.just(new InvoiceCountSummary(0,0,0,0))));
    }

    public Mono<InvoiceCountSummary> mongoTemplateAggregation(LocalDate dateFrom, LocalDate dateTo) {


        var matchStage = match(Criteria.where("invoiceDate").gte(dateFrom).lte(dateTo));

        var groupStage = group().sum("amount").as("total").push(Aggregation.ROOT).as("docs");

        var firstProjectStage = project("total").andExclude("_id")
                .and(aggregationOperationContext -> filterConditions(InvoiceStatusEnum.PAID.name())).as("paid")
                .and(aggregationOperationContext -> filterConditions(InvoiceStatusEnum.CANCELED.name())).as("canceled")
                .and(aggregationOperationContext -> filterConditions(InvoiceStatusEnum.PENDING.name())).as("pending");

        var secondProjectStage = project( "total")
                .and(aggregationOperationContext -> new Document("$sum", "$paid.amount")).as("paid")
                .and(aggregationOperationContext -> new Document("$sum", "$canceled.amount")).as("canceled")
                .and(aggregationOperationContext -> new Document("$sum", "$pending.amount")).as("pending");

        var aggregation = Aggregation.newAggregation(matchStage, groupStage, firstProjectStage,secondProjectStage);

        var result = reactiveMongoTemplate.aggregate(aggregation, Invoice.class, InvoiceCountSummary.class);
        return Mono.from(result)
                .switchIfEmpty(Mono.defer(() -> Mono.just(new InvoiceCountSummary(0,0,0,0))));
    }

    private Document filterConditions(String invoiceStatus) {
        Document filterExpression = new Document();
        filterExpression.put("input", "$docs");
        filterExpression.put("as", "doc");
        filterExpression.put("cond", new Document("$eq", Arrays.<Object>asList("$$doc.status", invoiceStatus)));
        return new Document("$filter", filterExpression);
    }
}
