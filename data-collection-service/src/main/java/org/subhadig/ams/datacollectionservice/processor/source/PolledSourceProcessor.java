package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.subhadig.ams.datacollectionservice.config.source.PolledSourceConfig;

public abstract class PolledSourceProcessor extends SourceProcessor {
    
    ScheduledExecutorService executorService;
    
    ScheduledFuture<?> scheduledFuture;
    
    @Override
    public void start() {
        LOGGER.info("Starting the polling");
        executorService = createExecutorService();
        schedulePoll();
    }
    
    @Override
    public void stop() {
        executorService.shutdown();
        executorService = null;
    }
    
    ScheduledExecutorService createExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
    
    protected abstract Object processOnePoll();
    
    private void schedulePoll() {
        scheduledFuture = executorService.schedule( new PollThread() , 
                                                    ((PolledSourceConfig) config).getPollInterval(), 
                                                    TimeUnit.MILLISECONDS);
    }
    
    class PollThread implements Runnable {
        
        @Override
        public void run() {
            LOGGER.info("Processing one poll");
            if( !queue.offer( processOnePoll() ) ) {
                LOGGER.warn("Unable to push to queue");
            }
            schedulePoll();
        }
        
    }
}
