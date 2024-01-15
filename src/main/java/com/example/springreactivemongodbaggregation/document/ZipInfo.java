package com.example.springreactivemongodbaggregation.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

// https://github.com/spring-projects/spring-data-mongodb/blob/main/spring-data-mongodb/src/test/java/org/springframework/data/mongodb/core/aggregation/ZipInfo.java#L13
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection="zips")
public class ZipInfo {

    @Id
    private String id;

    private String city;

    private String state;

    @Field("pop")
    private int population;

    @Field("loc")
    private double[] location;
}
