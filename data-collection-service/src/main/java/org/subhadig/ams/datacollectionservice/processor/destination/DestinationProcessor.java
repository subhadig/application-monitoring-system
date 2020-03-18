package org.subhadig.ams.datacollectionservice.processor.destination;

import java.util.concurrent.BlockingQueue;

public abstract class DestinationProcessor {

    protected final BlockingQueue<Object> queue;
    
    public DestinationProcessor(BlockingQueue<Object> queue) {
        super();
        this.queue = queue;
    }

    public abstract void start();
    
    public abstract void stop();
}
