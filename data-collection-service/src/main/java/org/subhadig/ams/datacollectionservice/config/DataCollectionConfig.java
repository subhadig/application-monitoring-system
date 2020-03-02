package org.subhadig.ams.datacollectionservice.config;

/**
 * This class holds the DataCollection
 * configurations.
 * 
 * @author subhadig@github
 */
public class DataCollectionConfig {
	
	private SourceType source;
	
	private DestinationType destination;
	
	private SourceConfiguration sourceConfig;

	public DataCollectionConfig() {
		//Default constructor
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

	public SourceConfiguration getSourceConfig() {
		return sourceConfig;
	}

	public void setSourceConfig(SourceConfiguration sourceConfig) {
		this.sourceConfig = sourceConfig;
	}
}
