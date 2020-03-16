package org.subhadig.ams.datacollectionservice.processor;

import org.subhadig.ams.datacollectionservice.config.source.SourceType;
import org.subhadig.ams.datacollectionservice.processor.source.SourceProcessor;
import org.subhadig.ams.datacollectionservice.processor.source.SpringActuatorSourceProcessor;

public class ProcessorUtils {

    private ProcessorUtils() {}
    
    public static SourceProcessor createSourceProcessor(SourceType sourceType) {
        if(sourceType == SourceType.SpringActuator) {
            return new SpringActuatorSourceProcessor();
        }
        return null;
    }
}
