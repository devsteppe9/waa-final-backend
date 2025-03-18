FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN ./mvnw package

FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

