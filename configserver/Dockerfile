# Use OpenJDK 21 for Spring Boot 3+
FROM eclipse-temurin:21-jdk

# Set work directory
WORKDIR /app

# Copy the Maven/Gradle built JAR into container
COPY target/configserver-0.0.1-SNAPSHOT.jar app.jar

# Expose port (8888 for config server)
EXPOSE 8888

# Run the app
ENTRYPOINT ["java","-jar","/app/app.jar"]