package org.subhadig.ams.datacollectionservice.response.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.subhadig.ams.datacollectionservice.response.Response;

/**
 * @author subhadig@github
 *
 */
public interface ResponseRepository extends MongoRepository<Response, String> {

    @Async
    <S extends Response> S save(S entity);
}
