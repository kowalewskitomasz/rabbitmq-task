# RabbitMQ task
### How to run the project?
1. Go into "docker-compose" directory
2. Run `./build_docker_images.sh` - it will build all 3 services and create their Docker images
3. Run each scenario using designated script:
   1. `script_scenario1.sh`
   1. `script_scenario2.sh`
   1. `script_scenario3.sh`
   1. `script_scenario4.sh`

#### Scenario 1 results:
![image](https://user-images.githubusercontent.com/26500277/179424170-b6227ad3-11cb-4bbd-9c98-3bb4b11e292a.png)

#### Scenario 2 results
- additional instances of services are spun up by defining them manually in docker-compose file 

![image](https://user-images.githubusercontent.com/26500277/179424226-bf9547d2-3228-4e03-8187-ce5f23fe06b7.png)

#### Scenario 3 results:
- only first task processed by each Worker ended successfully. All others got stale during the 8s processing

![image](https://user-images.githubusercontent.com/26500277/179424339-4ef19648-1e06-42ca-b460-138e2db712b8.png)

#### Scenario 4 results 
- setup with 4 Producers and 8 Workers
- used throwing ImmediateRequeueAmqpException for requeuing failed tasks
- still used 8s processing time

![image](https://user-images.githubusercontent.com/26500277/179424506-327f7744-53dc-4bb6-b097-0308dcfb549f.png)

#### Scenario 4 results v2
- setup with 4 Producers and 12 Workers

![image](https://user-images.githubusercontent.com/26500277/179425073-f5b1d421-3956-4147-82f4-5ab34adfcce1.png)

#### Scenario 4 results v3
- setup with 4 Producers and 16 Workers

![image](https://user-images.githubusercontent.com/26500277/179425425-a7124191-1c2d-42fd-9909-dab9c5805cd1.png)

