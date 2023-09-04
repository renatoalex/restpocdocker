# Rest Poc

*Rest Poc* is a Rest Api Proof of Concept for handling users management.

## Prerequisites
Java Development Kit (JDK) version 17 or above installed on your machine

## Instalation

From root folder ``restpoc`` of the project execute the following commands:

# Commands

## Building

From project root run

```
.\gradlew build
```

## Docker image build

After building the project, run:

```
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Running docker image

```
docker run -p 8080:8080 ramp:rest-poc .
```

# Iterating

API Documentation

`http://localhost:8080/swagger-ui/index.html`


# Spring boot Standalone App

Main class

'com.ramp.poc.restpoc.RestpocApplication'

## Testing

Tests are available, run

```
.\gradlew clean test --tests=com.ramp.poc.restpoc.* 
```

## API

The Api is available at endpoint api/users

```
GET '/' - Retrieve all users
GET '/:id' - Retrieve user by its id
GET '/username/:username - Retrieve user by its username
POST '/' (body) - Create a new user 
PUT '/:id' (body) - Update an existing user
DELETE '/:id' - Remove an existing user
```