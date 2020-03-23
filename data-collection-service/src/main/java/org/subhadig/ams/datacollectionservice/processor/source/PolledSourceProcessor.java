package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.PolledSourceConfig;

public abstract class PolledSourceProcessor extends SourceProcessor {

    ScheduledExecutorService executorService;
    
    ScheduledFuture<?> scheduledFuture;
    
    Lock lock = new ReentrantLock();
    
    private static final int TIMEOUT_IN_MS = 5000;
    
    public PolledSourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        super(queue, config);
    }
    
    @Override
    public void start() {
        try {
            if(!lock.tryLock(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                LOGGER.error("Unable to acquire lock.");
                return;
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted while waiting for lock");
            Thread.currentThread().interrupt();
        }
        
        try {
            if(executorService == null) {
                LOGGER.info("Starting polling");
                executorService = createExecutorService();
                schedulePoll();
            } else {
                LOGGER.warn("Already started.");
            }
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void stop() {
        try {
            if(!lock.tryLock(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                LOGGER.error("Unable to acquire lock.");
                return;
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted while waiting for lock");
            Thread.currentThread().interrupt();
        }
        
        try {
            if(executorService == null) {
                LOGGER.warn("Already stopped.");
            } else {
                LOGGER.info("Stopping polling");
                if(scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                    scheduledFuture = null;
                }
                executorService.shutdown();
                executorService = null;
            }
        } finally {
            lock.unlock();
        }
    }
    
    ScheduledExecutorService createExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
    
    protected abstract Object processOnePoll();
    
    private void schedulePoll() {
        scheduledFuture = executorService.schedule( new PollThread() , 
                                                    ((PolledSourceConfig) config.getSourceConfig()).getPollInterval(), 
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
