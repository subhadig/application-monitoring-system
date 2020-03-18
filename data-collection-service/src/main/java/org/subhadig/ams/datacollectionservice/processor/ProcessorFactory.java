package org.subhadig.ams.datacollectionservice.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfigRepository;
import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;
import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SpringActuatorSourceProcessor;

/**
 * A factory class for {@link Processor}
 * 
 * @author subhadig@github
 */
@Component
public class ProcessorFactory {
    
    private static final int MAX_QUEUE_CAPACITY = 150;
    
    @Autowired
    private DataCollectionConfigRepository configRepository;

    public Processor createProcessor(DataCollectionConfig config) {
        
        BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(MAX_QUEUE_CAPACITY);
        SourceProcessor sourceProcessor = createSourceProcessor(config.getSource(), config.getSourceConfig(), queue);
        List<DestinationProcessor> destinationProcessors = new ArrayList<>(config.getDestinations().size());
        return new Processor(sourceProcessor, destinationProcessors);
    }
    
    private SourceProcessor createSourceProcessor(SourceType sourceType, SourceConfig sourceConfig, BlockingQueue<Object> queue ) {
        if(sourceType == SourceType.SpringActuator) {
            return new SpringActuatorSourceProcessor(queue, sourceConfig);
        } else {
            throw new IllegalArgumentException("Unknown source type.");
        }
    }
}
