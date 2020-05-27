#!/bin/sh

log()
{
    echo "\n[start-all] $1"
}

log "Creating network ams-net"
docker network create ams-net

log "Starting data provider service.."
docker run -d --rm --network ams-net -p 8081:8080 --name provider data-provider-service:latest

log "Printing the running Docker containers..\n\n"
docker ps
