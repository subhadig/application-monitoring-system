FROM openjdk:8-alpine
MAINTAINER subhadig@github
COPY target/*.jar /opt/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/app.jar"]
EXPOSE 8080