package org.subhadig.ams.datacollectionservice.processor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.subhadig.ams.datacollectionservice.processor.destination.DestinationProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;

/**
 * @author subhadig@github
 */
public class Processor {
    
    private static final int MAX_CAPACITY = 150;
    
    private SourceProcessor sourceProcessor;
    
    private List<DestinationProcessor> destinationProcessors;
    
    private BlockingQueue<Object> queue;
    
    public Processor() {
        this.queue = new LinkedBlockingQueue<Object>(MAX_CAPACITY);
    }
    
    public void start() {
        sourceProcessor.setQueue(queue);
        destinationProcessors.forEach(e -> e.setQueue(queue));
        
        sourceProcessor.start();
        destinationProcessors.forEach(DestinationProcessor::start);
    }
    
    public void stop() {
        sourceProcessor.stop();
        destinationProcessors.forEach(DestinationProcessor::stop);
    }

    public SourceProcessor getSourceProcessor() {
        return sourceProcessor;
    }

    public void setSourceProcessor(SourceProcessor sourceProcessor) {
        this.sourceProcessor = sourceProcessor;
    }

    public List<DestinationProcessor> getDestinationProcessors() {
        return destinationProcessors;
    }

    public void setDestinationProcessors(List<DestinationProcessor> destinationProcessors) {
        this.destinationProcessors = destinationProcessors;
    }
}
