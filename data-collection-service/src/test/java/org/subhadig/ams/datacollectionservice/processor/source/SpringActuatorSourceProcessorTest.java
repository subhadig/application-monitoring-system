package org.subhadig.ams.datacollectionservice.processor.source;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;
import org.subhadig.ams.datacollectionservice.response.MetricResponse;

public class SpringActuatorSourceProcessorTest {

    private SpringActuatorSourceProcessor processor;
    
    private static DataCollectionConfig config;
    
    @BeforeAll
    public static void setUpClass() {
        SpringActuatorSourceConfig sourceConfig = new SpringActuatorSourceConfig();
        sourceConfig.setProtocol("http");
        sourceConfig.setIpAddress("localhost");
        sourceConfig.setPort(8081);
        sourceConfig.setUserName("admin");
        sourceConfig.setPassword("admin");
        
        config = new DataCollectionConfig();
        config.setId(UUID.randomUUID().toString());
        config.setDescription("Test");
        config.setSourceConfig(sourceConfig);
    }
    
    @BeforeEach
    public void setUp() {
        processor = new SpringActuatorSourceProcessor(null, config);
    }
    
    @Test
    @Disabled("This test needs an actual server providing the responses")
    public void testProcessOnePoll() {
        MetricResponse response = processor.processOnePoll();
        System.out.println("Response::" + response);
        assertNotNull(response);
    }
}
