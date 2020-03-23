package org.subhadig.ams.datacollectionservice.response;

/**
 * This is a base class for all the collected responses.
 * 
 * @author subhadig@github
 */
public class Response {

    private String applicationId;
    
    private String appliationDescription;

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
