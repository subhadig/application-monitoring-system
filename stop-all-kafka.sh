#!/bin/sh

log()
{
    echo "\n[stop-all] $1"
}

log "Stoping data streaming service.."
docker stop kafka
docker stop zookeeper

log "Stopping network ams-net"
docker network rm ams-net
