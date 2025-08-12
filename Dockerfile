# Use an official maven image to build the spring boot app
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pm.xml and installing dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an offical open jdk image to run the spring boot app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the build Jar file from the application
COPY --from=build /app/target/Random-Email-Generator-0.0.1-SNAPSHOT.jar .

# Exopse port 8080
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java","-jar", "/app/Random-Email-Generator-0.0.1-SNAPSHOT.jar"]



