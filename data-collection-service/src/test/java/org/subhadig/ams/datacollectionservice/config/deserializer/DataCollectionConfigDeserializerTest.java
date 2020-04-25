package org.subhadig.ams.datacollectionservice.config.deserializer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.destination.DestinationType;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataCollectionConfigDeserializerTest {
 
    @Test
    public void testDeserialize() throws JsonMappingException, JsonProcessingException {
        String json = "{\n" + 
                "  \"description\": \"Test\",\n" + 
                "  \"destination\": \"Database\",\n" + 
                "  \"source\": \"SpringActuator\",\n" + 
                "  \"sourceConfig\": {\n" + 
                "    \"pollInterval\": 60000,\n" + 
                "    \"protocol\": \"http\",\n" + 
                "    \"ipAddress\": \"localhost\",\n" + 
                "    \"port\": 8081,\n" + 
                "    \"userName\": \"admin\",\n" + 
                "    \"password\": \"admin\"\n" + 
                "  }\n" + 
                "}\n" + 
                "";
        
        DataCollectionConfig config = new ObjectMapper().readValue(json, DataCollectionConfig.class);
        
        assertNull(config.getId());
        assertEquals("Test", config.getDescription());
        assertEquals(DestinationType.Database, config.getDestination());
        assertEquals(SourceType.SpringActuator, config.getSource());
        assertTrue(config.getSourceConfig() instanceof SpringActuatorSourceConfig);
        
        SpringActuatorSourceConfig sourceConfig = (SpringActuatorSourceConfig) config.getSourceConfig();
        assertEquals(60000, sourceConfig.getPollInterval());
        assertEquals("http", sourceConfig.getProtocol());
        assertEquals("localhost", sourceConfig.getIpAddress());
        assertEquals(8081, sourceConfig.getPort());
        assertEquals("admin", sourceConfig.getUserName());
        assertEquals("admin", sourceConfig.getPassword());
    }
}
