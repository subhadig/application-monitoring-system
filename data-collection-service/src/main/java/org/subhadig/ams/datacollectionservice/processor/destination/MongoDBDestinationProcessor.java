package org.subhadig.ams.datacollectionservice.processor.destination;

import java.util.concurrent.BlockingQueue;

import org.subhadig.ams.datacollectionservice.response.Response;
import org.subhadig.ams.datacollectionservice.response.repository.ResponseRepository;

/**
 * Class for pushing result objects to MongoDB.
 * 
 * @author subhadig@github
 */
public class MongoDBDestinationProcessor extends DestinationProcessor {
    
    private final ResponseRepository responseRepository;

    public MongoDBDestinationProcessor(BlockingQueue<Object> queue, ResponseRepository responseRepository) {
        super(queue);
        this.responseRepository = responseRepository;
    }

    @Override
    protected void processOne(Object o) {
        responseRepository.save((Response) o);
    }
}
