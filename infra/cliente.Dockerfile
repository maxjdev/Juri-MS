FROM maven:3.9.9-amazoncorretto-21 AS builder

WORKDIR /app

COPY ../ms-cliente/pom.xml .

RUN mvn dependency:go-offline -B

COPY ../ms-cliente/src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod
ENV CLIENTE_SERVICE_PORT=8081

COPY --from=builder /app/target/*.jar app.jar

EXPOSE ${CLIENTE_SERVICE_PORT}

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]