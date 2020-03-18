package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;

import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;

public class SpringActuatorSourceProcessor extends PolledSourceProcessor {

    public SpringActuatorSourceProcessor(BlockingQueue<Object> queue, SourceConfig config) {
        super(queue, config);
    }

    @Override
    protected Object processOnePoll() {
        // TODO Auto-generated method stub
        return null;
    }

}
