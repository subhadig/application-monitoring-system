package org.subhadig.ams.datacollectionservice.processor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subhadig.ams.datacollectionservice.common.ServiceDefinitions;
import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;

/**
 * @author subhadig@github
 */
public class Processor {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceDefinitions.PROCESSOR);
    
    private final SourceProcessor sourceProcessor;
    
    private final DestinationProcessor destinationProcessor;
    
    private final AtomicBoolean isRunning = new AtomicBoolean();
    
    public Processor(SourceProcessor sourceProcessor, DestinationProcessor destinationProcessor) {
        super();
        this.sourceProcessor = sourceProcessor;
        this.destinationProcessor = destinationProcessor;
    }

    public boolean start() {
        if(!isRunning.get()) {
            boolean sourceStatus = sourceProcessor.start();
            boolean destStatus = destinationProcessor.start();
            isRunning.set(true);
            return sourceStatus && destStatus;
        } else {
            LOGGER.warn("Processor is already started.");
            return false;
        }
    }
    
    public boolean stop() {
        if(isRunning.get()) {
            boolean sourceStatus = sourceProcessor.stop();
            boolean destStatus = destinationProcessor.stop();
            isRunning.set(false);
            return sourceStatus && destStatus;
        } else {
            LOGGER.warn("Processor is already stopped.");
            return false;
        }
    }
    
    public boolean isRunning() {
        return isRunning.get();
    }
}
