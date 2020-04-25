#!/bin/sh

log()
{
    echo "\n[stop-all] $1"
}

log "Stoping data provider service.."
docker stop provider

log "Stopping database service.."
docker stop db

log "Stoping data collector service.."
docker stop collector

log "Stopping network ams-net"
docker network rm ams-net
