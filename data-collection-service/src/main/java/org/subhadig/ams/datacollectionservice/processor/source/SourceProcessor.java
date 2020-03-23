package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subhadig.ams.datacollectionservice.common.ServiceDefinitions;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;

public abstract class SourceProcessor {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceDefinitions.SOURCE_PROCESSOR);

    protected final BlockingQueue<Object> queue;
    
    protected final DataCollectionConfig config;
    
    public SourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        this.queue = queue;
        this.config = config;
    }

    public abstract void start();
    
    public abstract void stop();
}
