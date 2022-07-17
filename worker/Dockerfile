FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
VOLUME /tmp
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5002,server=y,suspend=n
COPY ./build/libs/worker-0.0.1-SNAPSHOT.jar /app/lib/worker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/lib/worker-0.0.1-SNAPSHOT.jar"]
