FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/stream-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
EXPOSE 8890
ENTRYPOINT ["java","-jar","app.jar"]