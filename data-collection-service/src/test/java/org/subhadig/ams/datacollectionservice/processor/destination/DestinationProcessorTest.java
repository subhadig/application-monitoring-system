package org.subhadig.ams.datacollectionservice.processor.destination;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.awaitility.Awaitility.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class DestinationProcessorTest {

    private DestinationProcessor destinationProcessor;
    
    private BlockingQueue<Object> queue;
    
    private static final String ELEMENT_OBJECT = "element_object";
    
    @BeforeEach
    public void setup() {
        queue = new LinkedBlockingQueue<>();
        destinationProcessor = spy(new TestDestinationProcessor(queue));
    }
    
    @Test
    public void testStartStopProcessing() {
        testStart();
        testRunning();
        testStop();
    }
    
    private void testStart() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        doReturn(executorService).when(destinationProcessor).createExecutorService();
        queue.add(ELEMENT_OBJECT);
        
        assertTrue(destinationProcessor.start());
        
        await().atLeast(5, TimeUnit.SECONDS);
        
        assertEquals(executorService, destinationProcessor.executorService);
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(destinationProcessor).processOne(captor.capture());
        assertEquals(ELEMENT_OBJECT, captor.getValue());
        
        assertTrue(queue.isEmpty());
    }
    
    private void testRunning() {
        queue.add(ELEMENT_OBJECT);
        
        await().atLeast(5, TimeUnit.SECONDS);
        
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(destinationProcessor, times(2)).processOne(captor.capture());
        assertEquals(ELEMENT_OBJECT, captor.getValue());
        
        assertTrue(queue.isEmpty());
    }
    
    private void testStop() {
        ExecutorService executorService = destinationProcessor.executorService;
        Future<?> future = destinationProcessor.future;
        
        assertTrue(destinationProcessor.stop());
        
        assertNull(destinationProcessor.executorService);
        assertNull(destinationProcessor.future);
        assertTrue(executorService.isShutdown());
        assertTrue(future.isCancelled());
    }
    
    private static class TestDestinationProcessor extends DestinationProcessor {

        public TestDestinationProcessor(BlockingQueue<Object> queue) {
            super(queue);
        }

        @Override
        protected void processOne(Object o) {
            //Do nothing
        }
        
    }
}
