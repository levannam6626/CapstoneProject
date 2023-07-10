#
# Build stage
#
FROM maven:4.0.0-jdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-oracle
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","demo.jar"]