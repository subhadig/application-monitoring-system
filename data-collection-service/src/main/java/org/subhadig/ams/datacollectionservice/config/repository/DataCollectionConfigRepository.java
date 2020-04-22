package org.subhadig.ams.datacollectionservice.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;

/**
 * @author subhadig@github
 *
 */
public interface DataCollectionConfigRepository extends MongoRepository<DataCollectionConfig, String> {

}
