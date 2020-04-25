package org.subhadig.ams.datacollectionservice.processor.destination;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subhadig.ams.datacollectionservice.common.ServiceDefinitions;

/**
 * Base class for all destination processors.
 * 
 * @author subhadig@github
 */
public abstract class DestinationProcessor {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceDefinitions.DESTINATION_PROCESSOR);
    
    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);
    
    private static final int TIMEOUT_IN_MS = 5000;

    protected final BlockingQueue<Object> queue;
    
    Future<?> future;
    
    ExecutorService executorService;
    
    private Lock lock = new ReentrantLock();
    
    public DestinationProcessor(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

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
                executorService = createExecutorService();
                future = executorService.submit(new SubscriberThread());
            } else {
                LOGGER.warn("Already started.");
            }
        } finally {
            lock.unlock();
        }
        return true;
    }
    
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
                LOGGER.info("Stopping destination processor thread");
                if(future != null) {
                    future.cancel(true);
                    future = null;
                }
                executorService.shutdown();
                executorService = null;
                queue.clear();
            }
        } finally {
            lock.unlock();
        }
        return true;
    }
    
    ExecutorService createExecutorService() {
        return Executors.newSingleThreadExecutor(r -> new Thread(r, "dest-thread-" + THREAD_COUNTER.getAndIncrement()));
    }
    
    protected abstract void processOne(Object o);
    
    class SubscriberThread implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    processOne(queue.take());
                } catch (InterruptedException e) {
                    LOGGER.debug("Interrupted while waiting for elements", e);
                    Thread.currentThread().interrupt();
                }
            }
        }
        
    }
}
