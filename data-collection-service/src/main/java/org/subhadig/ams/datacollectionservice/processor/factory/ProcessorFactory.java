package org.subhadig.ams.datacollectionservice.processor.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.destination.DestinationType;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;
import org.subhadig.ams.datacollectionservice.processor.Processor;
import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.destination.MongoDBDestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SpringActuatorSourceProcessor;
import org.subhadig.ams.datacollectionservice.response.repository.ResponseRepository;

/**
 * A factory class for {@link Processor}
 * 
 * @author subhadig@github
 */
@Component
public class ProcessorFactory {
    
    private static final int MAX_QUEUE_CAPACITY = 150;
    
    @Autowired
    private ResponseRepository responseRepository;

    public Processor createProcessor(DataCollectionConfig config) {
        
        BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(MAX_QUEUE_CAPACITY);
        SourceProcessor sourceProcessor = createSourceProcessor(config, queue);
        DestinationProcessor destinationProcessor = createDestinationProcessor(config.getDestination(), queue);
        return new Processor(sourceProcessor, destinationProcessor);
    }
    
    private SourceProcessor createSourceProcessor(DataCollectionConfig config, BlockingQueue<Object> queue) {
        if(config.getSource() == SourceType.SpringActuator) {
            return new SpringActuatorSourceProcessor(queue, config);
        } else {
            throw new IllegalArgumentException("Unknown source type.");
        }
    }
    
    private DestinationProcessor createDestinationProcessor(DestinationType destinationType, BlockingQueue<Object> queue) {
        if(destinationType == DestinationType.Database) {
            return new MongoDBDestinationProcessor(queue, responseRepository);
        } else {
            throw new IllegalArgumentException("Unknown source type.");
        }

    }
}
