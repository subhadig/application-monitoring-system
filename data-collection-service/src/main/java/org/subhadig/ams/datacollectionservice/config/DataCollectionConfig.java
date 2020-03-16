package org.subhadig.ams.datacollectionservice.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.subhadig.ams.datacollectionservice.config.destination.DestinationType;
import org.subhadig.ams.datacollectionservice.config.source.SourceConfig;
import org.subhadig.ams.datacollectionservice.config.source.SourceType;

/**
 * This class holds the DataCollection
 * configurations.
 * 
 * @author subhadig@github
 */
@Document("data-collection-config")
public class DataCollectionConfig {
    
    @Id
    private String id;
    
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
