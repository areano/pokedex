# Pokedex API

Pokedex is a REST API that returns Pokemon information and translations.

## Installation

The project uses the following stack, make sure you have the required dependencies installed.

* Java 11 (required)
* Spring Boot
* Maven

## How to run it

To run the project locally using the spring boot plugin, please run

```shell
./mvnw spring-boot:run
```

To run the unit tests, please run

```shell
./mvnw spring-boot:run
```

## Docker

If you have docker installed in your machine, you can build the image and run it locally.

Build the image with the following command

```shell
docker build -t com.areano/pokedex .
```

Run the container

```shell
docker run -p 8080:8080 com.areano/pokedex
```

## Calling the API

Use the following commands for calling the endpoints

### Pokemon endpoint

```shell
curl -X GET http://localhost:8080/pokemon/pikachu
```

### Translation endpoint

```shell
curl -X GET http://localhost:8080/pokemon/translated/pikachu

```

## Production considerations

* Config server
* Caching requests
* Swagger api
* Docker layers
