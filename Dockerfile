FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests=false

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/target/fos-support-0.0.1.jar fos-support.jar

EXPOSE 8095
ENTRYPOINT ["java", "-jar", "fos-support.jar"]
