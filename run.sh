#!/bin/bash
echo
cd ./src/main/resources
npm install
cd ../../../
./gradlew run_prod $PORT