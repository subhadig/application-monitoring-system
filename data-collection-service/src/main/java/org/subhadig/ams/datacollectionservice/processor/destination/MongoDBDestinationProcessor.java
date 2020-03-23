package org.subhadig.ams.datacollectionservice.processor.destination;

import java.util.concurrent.BlockingQueue;

import org.subhadig.ams.datacollectionservice.config.DataCollectionConfigRepository;

/**
 * Class for pushing result objects to MongoDB.
 * 
 * @author subhadig@github
 */
public class MongoDBDestinationProcessor extends DestinationProcessor {
    
    private final DataCollectionConfigRepository configRepository;

    public MongoDBDestinationProcessor(BlockingQueue<Object> queue, DataCollectionConfigRepository configRepository) {
        super(queue);
        this.configRepository = configRepository;
    }

    @Override
    protected void processOne(Object o) {
        // TODO Auto-generated method stub
    }
}
