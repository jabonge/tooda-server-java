FROM openjdk:11-jdk

ENV PROFILE dev

COPY build/libs/tooda-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]
