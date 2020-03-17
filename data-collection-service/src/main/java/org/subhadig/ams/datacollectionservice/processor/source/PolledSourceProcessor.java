package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.subhadig.ams.datacollectionservice.config.source.PolledSourceConfig;

public abstract class PolledSourceProcessor extends SourceProcessor {
    
    ScheduledExecutorService executorService;
    
    public PolledSourceProcessor() {
    }

    @Override
    public void start() {
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
