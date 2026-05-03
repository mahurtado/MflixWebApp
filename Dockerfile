FROM us-central1-docker.pkg.dev/serverless-runtimes/google-22-full/runtimes/java17
COPY ./target/mflix-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
