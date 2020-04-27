#!/bin/sh

log()
{
    echo "\n[start-all] $1"
}
log "Creating network ams-net"
docker network create ams-net

log "Packaging data analytics service.."
docker build -t data-analytics-service:latest data-analytics-service/

log "Starting data analytics service.."
docker run -d --rm --network ams-net -p 9200:9200 -p 9300:9300 --name elasticsearch -e "discovery.type=single-node" data-analytics-service:latest

log "Packaging ui service.."
docker build -t ui-service:latest ui-service/

log "Starting ui service.."
docker run -d --rm --network ams-net -p 5601:5601 --name kibana ui-service:latest

log "Printing the running Docker containers..\n\n"
docker ps
