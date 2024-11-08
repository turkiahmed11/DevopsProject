# Use the official OpenJDK image directly
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file directly if it's already built
COPY target/kaddem-0.0.1.jar kaddem-0.0.1.jar

# Expose the port the app runs on
EXPOSE 8089

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/kaddem-0.0.1.jar"]
