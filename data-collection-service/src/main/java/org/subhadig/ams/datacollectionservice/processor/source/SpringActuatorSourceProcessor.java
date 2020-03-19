package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.concurrent.BlockingQueue;

import org.springframework.web.reactive.function.client.WebClient;
import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;

/**
 * A processor for Spring Actuator type sources.
 * 
 * @author subhadig@github
 */
public class SpringActuatorSourceProcessor extends PolledSourceProcessor {
    
    public SpringActuatorSourceProcessor(BlockingQueue<Object> queue, SourceConfig config) {
        super(queue, config);
    }

    @Override
    protected Object processOnePoll() {
        SpringActuatorSourceConfig sourceConfig = (SpringActuatorSourceConfig) this.config;
        WebClient webClient = WebClient
                                .builder()
                                .baseUrl(String.format("%s://%s:%s",
                                                       sourceConfig.getProtocol(),
                                                       sourceConfig.getIpAddress(),
                                                       sourceConfig.getPort()))
                                .build();
        return null;
    }

}
