package org.subhadig.ams.datacollectionservice.response.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.subhadig.ams.datacollectionservice.response.Response;

/**
 * @author subhadig@github
 *
 */
public interface ResponseRepository extends MongoRepository<Response, String> {

}
