FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S taskflow && adduser -S taskflow -G taskflow
WORKDIR /app
COPY --from=build /workspace/target/taskflow-api-1.0.0.jar app.jar
USER taskflow
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
