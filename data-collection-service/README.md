# Data Collector Service
- It currently supports collecting metrics from application exposing Spring Actuator *health* and *metrics* APIs.
- The *data-collection-configs* can be stored/deleted. Data collection starts as soon as a config is stored.
- The data collection for a saved *data-collection-config* can be started/stopped.
- It also uses the Spring Security module to secure the exposed REST end points.

## REST endpoints
- Swagger UI 				-> http://localhost:8080/swagger-ui.html
- Retrieve all configs 		-> GET http://localhost:8080/data-collection-configs
- Save or update a config	-> POST http://localhost:8080/data-collection-configs
- Retrieve config by id		-> GET http://localhost:8080/data-collection-configs/{id}
- Delete a config 			-> DELETE http://localhost:8080/data-collection-configs
- Get config status			-> GET http://localhost:8080/data-collection-configs/{id}/status
- Update config status		-> PUT http://localhost:8080/data-collection-configs/{id}/status/{status}

### Default credentials
- username: admin
- password: admin

### Save Config payload
```json
{
  "description": "Test",
  "destination": "Database",
  "source": "SpringActuator",
  "sourceConfig": {
	"pollInterval": 60000,
	"protocol": "http",
	"ipAddress": "provider",
	"port": 8080,
	"userName": "admin",
	"password": "admin"
  }
}
```

## Packaging
mvn package

## Running
docker run -d --rm -p 8080:8080 --name collector data-collector-service:0.0.1-SNAPSHOT