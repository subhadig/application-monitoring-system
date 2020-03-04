package org.subhadig.ams.datacollectionservice.config;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataCollectionConfigRepository extends MongoRepository<DataCollectionConfig, String> {

}
