#!/bin/bash
isAppRunning = `pgrep java`
if [[ -n  $isAppRunning ]]; then
    killall java
fi
