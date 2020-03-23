package org.subhadig.ams.datacollectionservice.processor;

import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;

/**
 * @author subhadig@github
 */
public class Processor {
    
    private final SourceProcessor sourceProcessor;
    
    private final DestinationProcessor destinationProcessor;
    
    public Processor(SourceProcessor sourceProcessor, DestinationProcessor destinationProcessor) {
        super();
        this.sourceProcessor = sourceProcessor;
        this.destinationProcessor = destinationProcessor;
    }

    public void start() {
        sourceProcessor.start();
        destinationProcessor.start();
    }
    
    public void stop() {
        sourceProcessor.stop();
        destinationProcessor.stop();
    }
}
