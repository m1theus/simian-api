#!/bin/bash

docker-compose -p simian-mongodb up -d

SPRING_PROFILES_ACTIVE=local SERVER_PORT=8080 ./gradlew bootRun
