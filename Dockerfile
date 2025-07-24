FROM openjdk:18-jdk-slim
COPY ./target/mflix-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
