#!/bin/bash
if [ ! -f /usr/bin/java ]; then
    yum install -y java-1.8.0-openjdk
fi

