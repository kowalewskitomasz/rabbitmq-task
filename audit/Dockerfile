FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
VOLUME /tmp
COPY ./build/libs/audit-0.0.1-SNAPSHOT.jar /app/lib/audit-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/lib/audit-0.0.1-SNAPSHOT.jar"]
