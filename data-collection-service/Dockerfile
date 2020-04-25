FROM openjdk:8-alpine
MAINTAINER subhadig@github
ARG jarfile
COPY $jarfile /opt/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/app.jar"]
EXPOSE 8080
