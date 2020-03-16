package org.subhadig.ams.datacollectionservice.processor.source;

public class PolledSourceProcessorTest {

    private PolledSourceProcessor processor;
    
    public void setup() {
        processor = new TestPolledSourceProcessor();
    }
    
    public void testStartStop() {
        
    }
    
    private static class TestPolledSourceProcessor extends PolledSourceProcessor {

        @Override
        protected Object processOnePoll() {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
