#!/bin/bash

echo "Starting up environment for Scenario 4"
docker-compose -f docker-compose_scenario4.yml up -d
sleep 120s
echo "Stopping production of tasks"
curl --silent --output nul --location --request POST 'http://localhost:5100/scheduler/stop'
curl --silent --output nul --location --request POST 'http://localhost:5101/scheduler/stop'
curl --silent --output nul --location --request POST 'http://localhost:5102/scheduler/stop'
curl --silent --output nul --location --request POST 'http://localhost:5103/scheduler/stop'
sleep 5s
echo "Resetting audit stats"
curl --silent --output nul --location --request POST 'http://localhost:5003/audit/reset'
sleep 2s
echo "Starting the production of tasks"
curl --silent --output nul --location --request POST 'http://localhost:5100/scheduler/start'
curl --silent --output nul --location --request POST 'http://localhost:5101/scheduler/start'
curl --silent --output nul --location --request POST 'http://localhost:5102/scheduler/start'
curl --silent --output nul --location --request POST 'http://localhost:5103/scheduler/start'
sleep 30s
echo "Stats after 30s of processing"
curl --location --request GET 'http://localhost:5003/audit/stats'
echo "Shutting down the environment"
docker-compose -f docker-compose_scenario4.yml down
