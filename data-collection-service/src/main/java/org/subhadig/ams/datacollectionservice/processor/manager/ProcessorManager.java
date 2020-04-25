package org.subhadig.ams.datacollectionservice.processor.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.repository.DataCollectionConfigRepository;
import org.subhadig.ams.datacollectionservice.processor.Processor;
import org.subhadig.ams.datacollectionservice.processor.factory.ProcessorFactory;

/**
 * This class manages all the running processors.
 * 
 * @author subhadig@github
 */
@Component
public class ProcessorManager {

    @Autowired
    private ProcessorFactory processorFactory;
    
    @Autowired
    private DataCollectionConfigRepository configRepository;
    
    private Map<String, Processor> processorsMap = new HashMap<>();
    
    @PostConstruct
    void init() {
        List<DataCollectionConfig> configs = configRepository.findAll();
        configs.forEach(this::addNewProcessor);
    }
    
    public void addNewProcessor(DataCollectionConfig config) {
        Processor processor = processorFactory.createProcessor(config);
        processorsMap.put(config.getId(), processor);
        processor.start();
    }
    
    public boolean startProcessor(String configId) {
        return processorsMap.get(configId).start();
    }
    
    public boolean stopProcessor(String configId) {
        return processorsMap.get(configId).stop();
    }
    
    public boolean getProcessorStatus(String configId) {
        return processorsMap.get(configId).isRunning();
    }
}
