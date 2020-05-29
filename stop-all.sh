#!/bin/sh

log()
{
    echo "\n[stop-all] $1"
}

log "Stoping data collector service.."
docker stop collector

log "Stoping data streaming service.."
docker stop kafka
docker stop zookeeper

log "Stopping database service.."
docker stop db

log "Stoping data provider service.."
docker stop provider

log "Stopping network ams-net"
docker network rm ams-net
