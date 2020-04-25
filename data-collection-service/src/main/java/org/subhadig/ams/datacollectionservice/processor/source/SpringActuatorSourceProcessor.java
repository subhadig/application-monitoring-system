package org.subhadig.ams.datacollectionservice.processor.source;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;
import org.subhadig.ams.datacollectionservice.response.MetricResponse;

import net.minidev.json.JSONObject;
import reactor.netty.http.client.HttpClient;

/**
 * A processor for Spring Actuator type sources.
 * 
 * @author subhadig@github
 */
public class SpringActuatorSourceProcessor extends PolledSourceProcessor<MetricResponse> {
    
    private String[] urls = {"/actuator/health", "/actuator/metrics"};
    
    private final WebClient webClient;
    
    public SpringActuatorSourceProcessor(BlockingQueue<Object> queue, DataCollectionConfig config) {
        super(queue, config);
        
        SpringActuatorSourceConfig sourceConfig = (SpringActuatorSourceConfig) config.getSourceConfig();
        webClient = WebClient.builder()
                             .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true).wiretap(true)))
                             .baseUrl(String.format("%s://%s:%s",
                                                    sourceConfig.getProtocol(),
                                                    sourceConfig.getIpAddress(),
                                                    sourceConfig.getPort()))
                             .defaultHeaders(header -> header.setBasicAuth(sourceConfig.getUserName(),
                                                                           sourceConfig.getPassword()))
                             .build();

    }

    @Override
    protected MetricResponse processOnePoll() {
        MetricResponse response = new MetricResponse();
        response.setApplicationId(config.getId());
        response.setAppliationDescription(config.getDescription());
        response.setTimestamp(Calendar.getInstance().getTimeInMillis());
        
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
        
        key = key.replace(".", "_");
        response.getMetricsMap().put(key, json);
    }

}
