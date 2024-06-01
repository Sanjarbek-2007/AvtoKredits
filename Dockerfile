FROM openjdk:17-alpine


# Set the working directory in the container
WORKDIR /app


# Make port 8080 available to the world outside this container
EXPOSE 8080


ARG JAR_FILE=target/*.jar
COPY ./target/AvtoKredits-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "./app.jar"]