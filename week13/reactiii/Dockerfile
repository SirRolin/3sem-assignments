# Use a Java runtime as a base image
FROM eclipse-temurin:17-alpine

# This is the jar file that you want to run
COPY target/reactiii-1.0-SNAPSHOT-shaded.jar /app.jar

# This is the port that your javalin application will listen on
EXPOSE 7070

# This is the command that will be run when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]