#!/bin/sh

log()
{
    echo "\n[stop-all] $1"
}

log "Stoping data analytics service.."
docker stop elasticsearch

log "Stoping ui service.."
docker stop kibana

log "Stopping network ams-net"
docker network rm ams-net
