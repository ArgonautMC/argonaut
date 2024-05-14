FROM openjdk:17

CMD ["./gradlew", "clean", "build"]

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} argonaut.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/argonaut.jar"]