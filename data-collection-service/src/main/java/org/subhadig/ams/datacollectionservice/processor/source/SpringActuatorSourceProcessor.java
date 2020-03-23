package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;
import org.subhadig.ams.datacollectionservice.response.MetricResponse;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * A processor for Spring Actuator type sources.
 * 
 * @author subhadig@github
 */
public class SpringActuatorSourceProcessor extends PolledSourceProcessor {
    
    private List<String> urlList = Arrays.asList("/actuator/health",
                                                 "/actuator/metrics");
    
    public SpringActuatorSourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        super(queue, config);
    }

    @Override
    protected Object processOnePoll() {
        SpringActuatorSourceConfig sourceConfig = (SpringActuatorSourceConfig) this.config.getSourceConfig();
        WebClient webClient = WebClient
                                .builder()
                                .baseUrl(String.format("%s://%s:%s",
                                                       sourceConfig.getProtocol(),
                                                       sourceConfig.getIpAddress(),
                                                       sourceConfig.getPort()))
                                .build();
        
        MetricResponse response = new MetricResponse();
        response.setApplicationId(config.getId());
        response.setAppliationDescription(config.getDescription());
        response.getMetricsMap().put("health", null);
        
        ClientResponse res = webClient.method(HttpMethod.GET).uri(urlList.get(0)).exchange().block();
        JsonNode
        return null;
    }

}
