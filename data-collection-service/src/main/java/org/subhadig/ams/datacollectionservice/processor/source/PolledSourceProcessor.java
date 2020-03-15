package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.subhadig.ams.datacollectionservice.config.PolledSourceConfig;

public abstract class PolledSourceProcessor extends SourceProcessor {
    
    private ScheduledExecutorService executorService;
    
    public PolledSourceProcessor() {
    }

    @Override
    public void start() {
        executorService = Executors.newScheduledThreadPool(1);
        schedulePoll();
    }
    
    @Override
    public void stop() {
        executorService.shutdown();
    }
    
    protected abstract Object processOnePoll();
    
    private void schedulePoll() {
        executorService.schedule( new PollThread() , 
                                  ((PolledSourceConfig) config).getPollInterval(), 
                                  TimeUnit.MILLISECONDS);
    }
    
    private class PollThread implements Runnable {
        
        @Override
        public void run() {
            if( queue.offer( processOnePoll() ) ) {
                LOGGER.warn("Unable to push to queue");
            }
            schedulePoll();
        }
        
    }
}
