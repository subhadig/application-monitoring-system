# Data Collector Service
- It uses the Spring Actuator module to generate runtime metrics
and exposes over REST. Protocol used is HTTP.
- It also uses the Spring Security module to secure the exposed REST end points.

## REST endpoints
- Swagger UI 			-> http://localhost:8080/swagger-ui.html#/
- Retrieve all configs 	-> GET http://localhost:8080/data-collection-configs
- Save a configs 		-> POST http://localhost:8080/data-collection-configs
- Update a configs 		-> PUT http://localhost:8080/data-collection-configs

### Default credentials
- username: admin
- password: admin

## Packaging
mvn package

## Running
docker run -d --rm -p 8080:8080 --name simulator data-simulator-service:0.0.1-SNAPSHOT