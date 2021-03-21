#!/bin/sh
java \
-Dspring.profiles.active=dev \
-Xms100m \
-Xmx500m \
-jar ../build/libs/holycard-0.0.1-SNAPSHOT.jar &