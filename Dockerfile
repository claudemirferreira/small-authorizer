# Etapa 1: Build do Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Etapa 2: Executar o JAR no OpenJDK
FROM openjdk:21
WORKDIR /opt
ENV PORT=8080
ENV PROFILE=dev
EXPOSE 8080
COPY --from=build /app/target/*-SNAPSHOT.jar /opt/app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "mysqldb:3306", "-t", "5", "--", "java", "-jar", "/opt/app.jar"]
