package com.example.springreactivemongodbaggregation.service;

import com.example.springreactivemongodbaggregation.document.ZipInfo;
import com.example.springreactivemongodbaggregation.model.ZipInfoStats;
import com.example.springreactivemongodbaggregation.model.StateCities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@RequiredArgsConstructor
@Service
public class ZipInfoService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;


    /**
     * Get Largest and Smallest Cities by State
     * @return Flux of ZipInfoStats
     */
    public Flux<ZipInfoStats> getLargestAndSmallestCitiesByState() {
/*
        """
             db.getCollection("zips").aggregate([
                               {
                                   $group: {
                                       _id: {
                                           state: '$state',
                                           city: '$city'
                                       },
                                       pop: {
                                           $sum: '$pop'
                                       }
                                   }
                               },
                               {
                                   $sort: {
                                       pop: 1,
                                       '_id.state': 1,
                                       '_id.city': 1
                                   }
                               },
                               {
                                   $group: {
                                       _id: '$_id.state',
                                       biggestCity: {
                                           $last: '$_id.city'
                                       },
                                       biggestPop: {
                                           $last: '$pop'
                                       },
                                       smallestCity: {
                                           $first: '$_id.city'
                                       },
                                       smallestPop: {
                                           $first: '$pop'
                                       }
                                   }
                               },
                               {
                                   $project: {
                                       _id: 0,
                                       state: '$_id',
                                       biggestCity: {
                                           name: '$biggestCity',
                                           pop: '$biggestPop'
                                       },
                                       smallestCity: {
                                           name: '$smallestCity',
                                           pop: '$smallestPop'
                                       }
                                   }
                               },
                               {
                                   $sort: {
                                       state: 1
                                   }
                               }
                           
                           ])
        """
        */


        TypedAggregation<ZipInfo> aggregation = newAggregation(ZipInfo.class, //
                group("state", "city").sum("population").as("pop"), //
                sort(ASC, "pop", "state", "city"), //
                group("state") //
                        .last("city").as("biggestCity") //
                        .last("pop").as("biggestPop") //
                        .first("city").as("smallestCity") //
                        .first("pop").as("smallestPop"), //
                project() //
                        .and("state").previousOperation() //
                        .and("biggestCity").nested(bind("name", "biggestCity").and("pop", "biggestPop")) //
                        .and("smallestCity").nested(bind("name", "smallestCity").and("pop", "smallestPop")), //
                sort(ASC, "state") //
        );

        return reactiveMongoTemplate.aggregate(aggregation, ZipInfo.class, ZipInfoStats.class);

    }


    public Flux<StateCities> getCitiesByState() {
/*
        """
                db.getCollection('zips').aggregate(
                    [
                        {
                            $group: {
                                _id: "$state",
                                cities: { $push: "$city" },
                            },
                        },

                        {
                            $project: {
                                _id: 0,
                                state: "$_id",
                                cities: "$cities",
                            },
                        },
                        {
                            $sort: { state: 1 },
                        },
                    ]
                );
        """*/
        var groupStage = group("$state").push("$city").as("cities");
        var projectStage = project().andExclude("_id").and("$_id").as("state").and("$cities").as("cities");
        var sortingStage = sort(ASC, "state");

        var aggregation = newAggregation(groupStage, projectStage, sortingStage);

        return reactiveMongoTemplate.aggregate(aggregation, ZipInfo.class, StateCities.class);
    }





}
