package com.example.springreactivemongodbaggregation.repository;

import com.example.springreactivemongodbaggregation.document.Invoice;
import com.example.springreactivemongodbaggregation.model.InvoiceCountSummary;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface InvoiceRepository extends ReactiveMongoRepository<Invoice, String> {

    @Aggregation(pipeline = {
            "{$match: {'invoiceDate': {$gte: ?0, $lte: ?1 }}}",
            "{$group: {_id: null,total: {$sum: '$amount'},docs: {$push: '$$ROOT'}}}",
            "{$project: {_id: 0,total: 1, paid: { $filter: { input: '$docs', as: 'doc',cond: {$eq: ['$$doc.status','PAID']}}},canceled: { $filter: { input: '$docs',as: 'doc',cond: {$eq: ['$$doc.status','CANCELED']}}}, pending: { $filter: { input: '$docs',as: 'doc',cond: {$eq: ['$$doc.status','PENDING']}}}}}",
            "{$project: { total: 1, paid: { $sum: '$paid.amount'}, canceled: {$sum: '$canceled.amount'}, pending: {$sum: '$pending.amount'}}}"
    })
    Mono<InvoiceCountSummary> findInvoiceSummary(LocalDate startDate, LocalDate endDate);
}
