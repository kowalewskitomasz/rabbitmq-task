#!/bin/bash

echo "Starting up environment for Scenario 1"
docker-compose -f docker-compose_scenario1.yml up -d
sleep 30s
echo "Stopping production of tasks"
curl --silent --output nul --location --request POST 'http://localhost:5001/scheduler/stop'
sleep 5s
echo "Resetting audit stats"
curl --silent --output nul --location --request POST 'http://localhost:5003/audit/reset'
sleep 2s
echo "Starting the production of tasks"
curl --silent --output nul --location --request POST 'http://localhost:5001/scheduler/start'
sleep 30s
echo "Stats after 30s of processing"
curl --location --request GET 'http://localhost:5003/audit/stats'
echo "Shutting down the environment"
docker-compose -f docker-compose_scenario1.yml down
