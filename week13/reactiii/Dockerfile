# Use a Java runtime as a base image
FROM eclipse-temurin:17-alpine

run dir -s target/

# This is the jar file that you want to run
COPY week13/reactiii/target/app.jar /app.jar

# copy library jar file
# COPY target/../../../myLibrary/library/target/library-1.0-SNAPSHOT.jar ../../myLibrary/library/target/library-1.0-SNAPSHOT.jar

# This is the port that your javalin application will listen on
EXPOSE 7070

# This is the command that will be run when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]