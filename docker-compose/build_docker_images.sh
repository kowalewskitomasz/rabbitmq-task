#!/bin/bash

echo "Building each service together with docker image"
echo "Building Audit service"
cd ../audit
./gradlew build -x test
./gradlew docker
echo "Building Producer service"
cd ../producer
./gradlew build -x test
./gradlew docker
echo "Building Worker service"
cd ../worker
./gradlew build -x test
./gradlew docker
