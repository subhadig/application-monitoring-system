package org.subhadig.ams.datacollectionservice.config.source;

public abstract class PolledSourceConfig extends SourceConfig {
    
    private int pollInterval;

    public int getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(int pollInterval) {
        this.pollInterval = pollInterval;
    }
}
