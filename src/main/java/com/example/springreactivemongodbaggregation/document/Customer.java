package com.example.springreactivemongodbaggregation.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection="customer")
public class Customer {

    @Id
    private String id;

    @NotNull
    private String name;
}
