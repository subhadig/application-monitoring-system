package org.subhadig.ams.datacollectionservice.config;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author subhadig@github
 *
 */
public interface DataCollectionConfigRepository extends MongoRepository<DataCollectionConfig, String> {

}
