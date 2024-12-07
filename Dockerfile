FROM openjdk:17
LABEL authors="ashfaq"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8090

# Run the App
ENTRYPOINT ["java", "-jar", "/app.jar"]