FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
VOLUME /tmp
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5004,server=y,suspend=n
COPY ./build/libs/producer-0.0.1-SNAPSHOT.jar /app/lib/producer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/lib/producer-0.0.1-SNAPSHOT.jar"]
