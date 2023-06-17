package com.example.springreactivemongodbaggregation.document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection="invoice")
public class Invoice {

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 8)
    private String reference;

    /**
     * When this invoice was generated
     */
    private LocalDateTime invoiceDate;

    private Customer customer;

    private InvoiceStatusEnum status = InvoiceStatusEnum.PENDING;
}
