package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subhadig.ams.datacollectionservice.common.ServiceDefinitions;
import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;

public abstract class SourceProcessor {
    
    protected static Logger LOGGER = LoggerFactory.getLogger(ServiceDefinitions.SOURCE_PROCESSOR);

    protected BlockingQueue<Object> queue;
    
    protected SourceConfig config;
    
    public abstract void start();
    
    public abstract void stop();

    public BlockingQueue<Object> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

    public SourceConfig getConfig() {
        return config;
    }

    public void setConfig(SourceConfig config) {
        this.config = config;
    }
}
