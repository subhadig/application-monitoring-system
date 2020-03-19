package org.subhadig.ams.datacollectionservice.config.source;

/**
 * @author subhadig@github
 *
 */
public class SpringActuatorSourceConfig extends PolledSourceConfig {
    
    private static final long serialVersionUID = -7763669962064300790L;

    private String protocol;

    private String ipAddress;
    
    private int port;
    
    private String userName;
    
    private String password;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
