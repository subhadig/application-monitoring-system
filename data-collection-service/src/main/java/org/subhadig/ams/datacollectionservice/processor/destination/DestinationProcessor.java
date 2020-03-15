package org.subhadig.ams.datacollectionservice.processor.destination;

import java.util.concurrent.BlockingQueue;

public abstract class DestinationProcessor {

    protected BlockingQueue<Object> queue;
    
    public abstract void start();
    
    public abstract void stop();

    public BlockingQueue<Object> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Object> queue) {
        this.queue = queue;
    }
}
