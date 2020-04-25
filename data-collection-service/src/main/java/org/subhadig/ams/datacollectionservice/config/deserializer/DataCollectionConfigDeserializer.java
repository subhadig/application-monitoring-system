package org.subhadig.ams.datacollectionservice.config.deserializer;

import static org.subhadig.ams.datacollectionservice.config.source.SourceType.*;

import java.io.IOException;

import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.destination.DestinationType;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;
import org.subhadig.ams.datacollectionservice.config.source.SpringActuatorSourceConfig;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A deserializer for {@link DataCollectionConfig}.
 * 
 * @author subhadig@github
 */
public class DataCollectionConfigDeserializer extends StdDeserializer<DataCollectionConfig> {

    private static final long serialVersionUID = 225374145134837052L;
    
    protected DataCollectionConfigDeserializer() {
        super(DataCollectionConfig.class);
    }
    

    @Override
    public DataCollectionConfig deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode rootNode = p.readValueAsTree();
        
        ObjectMapper mapper = new ObjectMapper();
        
        DataCollectionConfig dataCollectionConfig = new DataCollectionConfig();
        
        JsonNode id = rootNode.get("id");
        if(id != null) {
            dataCollectionConfig.setId(id.asText(null));
        }
        
        JsonNode desc = rootNode.get("description");
        if(desc != null) {
            dataCollectionConfig.setDescription(desc.asText(null));
        }
        
        JsonNode source = rootNode.get("source");
        if(source != null && source.asText(null) != null) {
            dataCollectionConfig.setSource(SourceType.valueOf(source.asText(null)));
        }
        
        JsonNode destination = rootNode.get("destination");
        if(destination != null && destination.asText(null) != null) {
            dataCollectionConfig.setDestination(DestinationType.valueOf(destination.asText(null)));
        }
        
        if(dataCollectionConfig.getSource() == SpringActuator) {
            dataCollectionConfig.setSourceConfig(mapper.convertValue(rootNode.get("sourceConfig"), SpringActuatorSourceConfig.class));
        }
        
        return dataCollectionConfig;
    } 
}
