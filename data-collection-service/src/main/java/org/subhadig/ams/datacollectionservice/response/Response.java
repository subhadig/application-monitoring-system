package org.subhadig.ams.datacollectionservice.response;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is a base class for all the collected responses.
 * 
 * @author subhadig@github
 */
@Document("data-collection-config")
public class Response {
    
    @Id
    private String id;

    private String applicationId;
    
    private String appliationDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAppliationDescription() {
        return appliationDescription;
    }

    public void setAppliationDescription(String appliationDescription) {
        this.appliationDescription = appliationDescription;
    }
}
