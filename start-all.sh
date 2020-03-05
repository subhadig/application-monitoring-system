#!/bin/sh

log()
{
    echo "\n[start-all] $1"
}

log "Packaging data provider service.."
mvn clean package -f data-provider-service/pom.xml -DskipTests
docker build -t data-provider-service:latest --build-arg jarfile="target/data-provider-service-*.jar" data-provider-service/

log "Starting data provider service.."
docker run -d --rm -p 8081:8080 --name provider data-provider-service:latest

log "Packaging database service.."
docker build -t database-service:latest database-service/

log "Starting database service.."
docker run -d --rm -p 27017:27017 --name db -e MONGO_INITDB_ROOT_USERNAME=mongouser -e MONGO_INITDB_ROOT_PASSWORD=mongouser database-service:latest

log "Printing the running Docker containers..\n\n"
docker ps
