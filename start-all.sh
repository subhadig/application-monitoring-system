#!/bin/sh

log()
{
    echo "\n[start-all] $1"
}
log "Creating network ams-net"
docker network create ams-net

log "Packaging data provider service.."
mvn clean package -f data-provider-service/pom.xml -DskipTests
docker build -t subhadig/data-provider-service:latest --build-arg jarfile="target/data-provider-service-*.jar" data-provider-service/

log "Starting data provider service.."
docker run -d --rm --network ams-net -p 8081:8080 --name provider subhadig/data-provider-service:latest

log "Packaging database service.."
docker build -t subhadig/database-service:latest database-service/

log "Starting database service.."
docker run -d --rm --network ams-net -p 27017:27017 --name db\
                                                        -e MONGO_INITDB_ROOT_USERNAME=mongouser\
                                                        -e MONGO_INITDB_ROOT_PASSWORD=mongouser\
                                                        subhadig/database-service:latest

log "Building data streaming service.."
docker build -t subhadig/zookeeper:latest -f data-streaming-service/Dockerfile.zookeeper data-streaming-service/
docker build -t subhadig/kafka:latest -f data-streaming-service/Dockerfile.kafka data-streaming-service/


log "Starting data streaming service.."

docker run -d --rm --network ams-net -p 2181:2181 --name zookeeper -e ALLOW_ANONYMOUS_LOGIN=yes subhadig/zookeeper:latest

docker run -d --rm --network ams-net -p 9092:9092 -p 29092:29092 --name kafka\
                                                               -e ALLOW_PLAINTEXT_LISTENER=yes\
                                                               -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181\
                                                               -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT\
                                                               -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,PLAINTEXT_HOST://:29092\
                                                               -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,PLAINTEXT_HOST://localhost:29092\
                                                               subhadig/kafka:latest

log "Packaging data collection service.."
mvn clean package -f data-collection-service/pom.xml -DskipTests
docker build -t subhadig/data-collection-service:latest --build-arg jarfile="target/data-collection-service-*.jar" data-collection-service/

log "Starting data collection service.."
docker run -d --rm --network ams-net -p 8082:8080 --name collector subhadig/data-collection-service:latest

log "Printing the running Docker containers..\n\n"
docker ps
