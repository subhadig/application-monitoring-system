package org.subhadig.ams.datacollectionservice.processor;

import java.util.List;

import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;

/**
 * @author subhadig@github
 */
public class Processor {
    
    private final SourceProcessor sourceProcessor;
    
    private final List<DestinationProcessor> destinationProcessors;
    
    public Processor(SourceProcessor sourceProcessor, List<DestinationProcessor> destinationProcessors) {
        super();
        this.sourceProcessor = sourceProcessor;
        this.destinationProcessors = destinationProcessors;
    }

    public void start() {
        sourceProcessor.start();
        destinationProcessors.forEach(DestinationProcessor::start);
    }
    
    public void stop() {
        sourceProcessor.stop();
        destinationProcessors.forEach(DestinationProcessor::stop);
    }
}
