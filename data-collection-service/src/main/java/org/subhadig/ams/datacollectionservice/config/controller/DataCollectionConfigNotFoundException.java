package org.subhadig.ams.datacollectionservice.config.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @author subhadig@github
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataCollectionConfigNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -105239284655863424L;
    
    DataCollectionConfigNotFoundException(String msg) {
        super(msg);
    }
}
