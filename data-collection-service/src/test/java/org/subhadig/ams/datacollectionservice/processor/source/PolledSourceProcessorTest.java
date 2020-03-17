package org.subhadig.ams.datacollectionservice.processor.source;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;

public class PolledSourceProcessorTest {

    private PolledSourceProcessor processor;
    
    private BlockingQueue<Object> queue;
    
    private static final String RETURNED_OBJECT = "returned_object";
    private static final long POLL_INTERVAL = 500l;
    
    @BeforeEach
    public void setup() {
        processor = spy(new TestPolledSourceProcessor());
        SpringActuatorSourceConfig config = new SpringActuatorSourceConfig();
        config.setPollInterval(POLL_INTERVAL);
        processor.setConfig(config);
        
        queue = new LinkedBlockingQueue<Object>();
        processor.setQueue(queue);
    }
    
    @Test
    public void testStartStopScheduling() {
        testStart();
        testScheduledStart();
        testStop();
    }
    
    private void testStart() {
        ScheduledExecutorService mockExecutorService = spy(Executors.newScheduledThreadPool(1));
        doReturn(mockExecutorService).when(processor).createExecutorService();
        
        processor.start();
        
        assertEquals(mockExecutorService, processor.executorService);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        verify(mockExecutorService).schedule(any(Runnable.class), intervalCaptor.capture(), any());
        assertEquals(POLL_INTERVAL, intervalCaptor.getValue());
        
        try {
            processor.scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
            Thread.currentThread().interrupt();
        }
        
        assertEquals(1, queue.size());
        assertEquals(RETURNED_OBJECT, queue.poll());
    }
    
    private void testScheduledStart() {
        
        try {
            processor.scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
            Thread.currentThread().interrupt();
        }
        
        assertEquals(1, queue.size());
        assertEquals(RETURNED_OBJECT, queue.poll());
    }
    
    private void testStop() {
        ScheduledExecutorService executor = processor.executorService;
        
        processor.stop();
        
        assertTrue(executor.isShutdown());
        assertNull(processor.executorService);
    }
    
    private static class TestPolledSourceProcessor extends PolledSourceProcessor {

        @Override
        protected Object processOnePoll() {
            return RETURNED_OBJECT;
        }
        
    }
}
