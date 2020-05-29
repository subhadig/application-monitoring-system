#!/bin/sh

log()
{
    echo "\n[start-all] $1"
}

log "Creating network ams-net"
docker network create ams-net

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


log "Printing the running Docker containers..\n\n"
docker ps
