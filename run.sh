#!/bin/bash
echo
cd ./src/main/resources
npm install
cd ../../../
java -jar ./build/libs/beta-web.jar -Ddw.server.applicationConnectors[0].port=$PORT