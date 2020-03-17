package org.subhadig.ams.datacollectionservice.config.source;

public abstract class PolledSourceConfig extends SourceConfig {
    
    private long pollInterval;

    /**
     * Get poll interval in milliseconds
     * @return
     */
    public long getPollInterval() {
        return pollInterval;
    }

    /**
     * Set poll interval in milliseconds
     * @param pollInterval
     */
    public void setPollInterval(long pollInterval) {
        this.pollInterval = pollInterval;
    }
}
