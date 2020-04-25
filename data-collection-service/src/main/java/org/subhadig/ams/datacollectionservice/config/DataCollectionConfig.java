package org.subhadig.ams.datacollectionservice.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.subhadig.ams.datacollectionservice.config.deserializer.DataCollectionConfigDeserializer;
import org.subhadig.ams.datacollectionservice.config.destination.DestinationType;
import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class holds the DataCollection
 * configurations.
 * 
 * @author subhadig@github
 */
@Document("data-collection-configs")
@JsonDeserialize(using = DataCollectionConfigDeserializer.class)
public class DataCollectionConfig {
    
    @Id
    private String id;
    
    private String description;
    
    private SourceType source;
    
    private DestinationType destination;
    
    private SourceConfig sourceConfig;

    public DataCollectionConfig() {
        //Default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SourceType getSource() {
        return source;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }

    public DestinationType getDestination() {
        return destination;
    }

    public void setDestination(DestinationType destination) {
        this.destination = destination;
    }

    public SourceConfig getSourceConfig() {
        return sourceConfig;
    }

    public void setSourceConfig(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
}
