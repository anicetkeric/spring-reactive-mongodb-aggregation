package com.example.springreactivemongodbaggregation;

import com.example.springreactivemongodbaggregation.document.ZipInfo;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class SpringReactiveMongodbAggregationApplication {

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;


	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveMongodbAggregationApplication.class, args);
	}



/*
	@PostConstruct
	void insertZipData() {
		// Fill the collection if it is empty
		reactiveMongoTemplate.count(new Query(), ZipInfo.class)
				.subscribe(countData -> {
					if (countData == 0){
						List<Document> documents = parseDocuments();

						reactiveMongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, ZipInfo.class).insert(documents).execute().subscribe();
					}
				});
	}
*/


	// https://github.com/spring-projects/spring-data-mongodb/blob/main/spring-data-mongodb/src/test/java/org/springframework/data/mongodb/core/aggregation/AggregationTests.java#L150
	private List<Document> parseDocuments() {

		Scanner scanner = null;
		List<Document> documents = new ArrayList<>(30000);

		try {
			scanner = new Scanner(new BufferedInputStream(new ClassPathResource("initdb/zips.json").getInputStream()));
			while (scanner.hasNextLine()) {
				String zipInfoRecord = scanner.nextLine();
				documents.add(Document.parse(zipInfoRecord));
			}
		} catch (Exception e) {
			if (scanner != null) {
				scanner.close();
			}
			throw new RuntimeException("Could not load mongodb sample dataset", e);
		}

		return documents;
	}

}
