package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.PolledSourceConfig;

/**
 * @author subhadig@github
 */
public abstract class PolledSourceProcessor<T> extends SourceProcessor {

    ScheduledExecutorService executorService;
    
    ScheduledFuture<?> scheduledFuture;
    
    Lock lock = new ReentrantLock();
    
    private static final int TIMEOUT_IN_MS = 5000;
    
    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);
    
    public PolledSourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        super(queue, config);
    }
    
    @Override
    public boolean start() {
        try {
            if(!lock.tryLock(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                LOGGER.error("Unable to acquire lock.");
                return false;
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
        return true;
    }
    
    @Override
    public boolean stop() {
        try {
            if(!lock.tryLock(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                LOGGER.error("Unable to acquire lock.");
                return false;
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
                try {
                    if(executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                        LOGGER.info("Source executor service did not shutdown normally.");
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    LOGGER.info("Interrupted while waiting for temination of executor service.");
                    Thread.currentThread().interrupt();
                }
                executorService = null;
            }
        } finally {
            lock.unlock();
        }
        return true;
    }
    
    ScheduledExecutorService createExecutorService() {
        return Executors.newScheduledThreadPool(1, r -> new Thread(r, "source-thread-" + THREAD_COUNTER.getAndIncrement()));
    }
    
    protected abstract T processOnePoll();
    
    private void schedulePoll() {
        if(executorService != null) {
            LOGGER.info("Scheduling poll.");
            scheduledFuture = executorService.schedule( new PollThread() , 
                                                        ((PolledSourceConfig) config.getSourceConfig()).getPollInterval(), 
                                                        TimeUnit.MILLISECONDS);
        }
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
