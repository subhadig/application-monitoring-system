package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;
import org.subhadig.ams.datacollectionservice.response.MetricResponse;

import net.minidev.json.JSONObject;

/**
 * A processor for Spring Actuator type sources.
 * 
 * @author subhadig@github
 */
public class SpringActuatorSourceProcessor extends PolledSourceProcessor<MetricResponse> {
    
    private String[] urls = {"/actuator/health", "/actuator/metrics"};
    
    public SpringActuatorSourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        super(queue, config);
    }

    @Override
    protected MetricResponse processOnePoll() {
        SpringActuatorSourceConfig sourceConfig = (SpringActuatorSourceConfig) config.getSourceConfig();
        WebClient webClient = WebClient
                                .builder()
                                .baseUrl(String.format("%s://%s:%s",
                                                       sourceConfig.getProtocol(),
                                                       sourceConfig.getIpAddress(),
                                                       sourceConfig.getPort()))
                                .defaultHeaders(header -> header.setBasicAuth(sourceConfig.getUserName(),
                                                                              sourceConfig.getPassword()))
                                .build();
        
        MetricResponse response = new MetricResponse();
        response.setApplicationId(config.getId());
        response.setAppliationDescription(config.getDescription());
        
        storeResponse(webClient, response, urls[0], "health");
        storeMetrics(webClient, response, urls[1]);

        return response;
    }
    
    private void storeMetrics(WebClient webClient, 
                              MetricResponse response,
                              String url) {
        
        JSONObject json = webClient.method(HttpMethod.GET)
                           .uri(url)
                           .retrieve()
                           .bodyToMono(JSONObject.class)
                           .block();
        
        @SuppressWarnings("unchecked")
        List<String> names = (List<String>) json.get("names");
        
        names.parallelStream().forEach(name -> storeResponse(webClient,
                                                             response,
                                                             String.format("%s/%s", url, name),
                                                             name));
    }

    private void storeResponse(WebClient webClient, 
                               MetricResponse response,
                               String url,
                               String key) {

        JSONObject json = webClient.method(HttpMethod.GET)
                           .uri(url)
                           .retrieve()
                           .bodyToMono(JSONObject.class)
                           .block();
        
        response.getMetricsMap().put(key, json);
    }

}
