# Data Simulator Service
- It uses the Spring Actuator module to generate runtime metrics
and exposes over REST. Protocol used is HTTP.
- It also uses the Spring Security module to secure the exposed REST end points.

## REST endpoints
- http://localhost:8080/actuator
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/metrics
- http://localhost:8080/actuator/metrics/{metrics-name}

### Default credentials
- username: admin
- password: admin

## Packaging
mvn package

## Running
docker run -d --rm -p 8080:8080 --name simulator data-simulator-service:0.0.1-SNAPSHOT