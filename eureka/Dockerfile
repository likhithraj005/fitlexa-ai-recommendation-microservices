# Use OpenJDK 21 for Spring Boot 3+
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven build output (target/*.jar)
COPY target/*.jar app.jar

# Expose the Eureka port
EXPOSE 8761

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app/app.jar"]