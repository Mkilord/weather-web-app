FROM maven:3.9-eclipse-temurin-22 AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src /app/src
RUN mvn clean package -Pproduction -DskipTests vaadin:build-frontend

FROM eclipse-temurin:22-jre

WORKDIR /app

COPY --from=builder /app/target/weather-web-app-0.0.1-SNAPSHOT.jar /app/weather-web-app.jar

RUN if [ -d "/app/target/frontend" ]; then mkdir -p /app/frontend && cp -r /app/target/frontend/* /app/frontend/; fi

EXPOSE 8080

CMD ["java", "-jar", "/app/weather-web-app.jar"]
