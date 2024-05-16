# Use a Java runtime as a base image
FROM eclipse-temurin:17-alpine

WORKDIR /app

# This is the jar file that you want to run
COPY target/app.jar /app/app.jar

# This is the port that your javalin application will listen on
EXPOSE 7070

# This is the command that will be run when the container starts
ENTRYPOINT ["java", "-jar", "/app/app.jar"]