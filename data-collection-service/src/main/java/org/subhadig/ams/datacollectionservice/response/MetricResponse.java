package org.subhadig.ams.datacollectionservice.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A class for collected metric responses.
 * 
 * @author subhadig@github
 */
@Document("metric-responses")
public class MetricResponse extends Response {
    
    private Map<String, Object> metricsMap = new HashMap<>();

    public Map<String, Object> getMetricsMap() {
        return metricsMap;
    }
    
    @Override
    public String toString() {
        return metricsMap.toString();
    }
}
