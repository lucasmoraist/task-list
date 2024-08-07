FROM maven:3.8.7-eclipse-temurin-19-alpine AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:19-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV MAIL_TO=${MAIL_TO}
ENV JWT_SECRET=${JWT_SECRET}
ENV PROFILE=${PROFILE}
ENV DATABASE_HOST=${DATABASE_HOST}
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_NAME=${DATABASE_NAME}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
