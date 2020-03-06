# Spring Cloud Microservices
![Java CI with Maven](https://github.com/gcalsolaro/spring-cloud-microservices/workflows/Java%20CI%20with%20Maven/badge.svg)
> **Cloud-native sample application using microservices architecture powered by Spring Cloud.**


## Table of Contents

   * [Spring Cloud Microservices](#spring-cloud-microservices)
      * [Architecture](#architecture)
      * [Prerequisites](#prerequisites)
      * [Running Instructions](#running-instructions)
      * [Usage](#usage)
         * [Get a token](#get-a-token)
         * [Employee service](#employee-service)
         * [Employee service (Gataway)](#employee-service-gataway)
      * [Rest API](#rest-api)
         * [Employee Rest API](#employee-rest-api)
         * [Organization Rest API](#organization-rest-api)
         * [Department Rest API](#department-rest-api)
      

## Architecture

Microservice architectural style is an approach to developing a single application as a suite of small services.
In this example the technology stack used is provided by Spring Cloud, in particular:

* **_Spring Cloud Release Trains_** - Greenwich.RELEASE
* **_Spring Cloud Config_** - Centralized external configuration management
* **_Spring Cloud Security_** - Provides support for load-balanced OAuth2 rest client
* **_Spring Cloud Netflix_** - Service Discovery, Circuit Breaker and Client Side Load Balancer with Ribbon
* **_Spring Cloud Gateway_** - Provide a simple way to route to APIs
* **_Spring Cloud OpenFeign_** - Provides integrations for Spring Boot apps through autoconfiguration

## Prerequisites
* **_JDK 8_** - Install JDK 1.8 version
* **_Maven_** - Download latest version
* **_Docker Toolbox_** - Install Docker Toolbox on your machine

## Running Instructions

```bash
$ mvn clean install
$ docker-compose up --build -d
```

## Usage
### Get a token:
```sh
$ curl -X POST -vu acme:acmesecret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&client_secret=acmesecret&client_id=acme"
```

### Employee service: 
```bash
$ curl http://localhost:8082/<METHOD> -H "Authorization: Bearer <YOUR TOKEN>"
```

### Employee service (Gataway): 
```bash
$ curl http://localhost:9000/employee/<METHOD> -H "Authorization: Bearer <YOUR TOKEN>"
```

## Rest API

### Employee Rest API

Method | URI | Description | Parameters |
--- | --- | --- | --- |
`GET` | */employee/* |
`GET` | */employee/{id}* |
`GET` | */employee/department/{departmentId}* |
`GET` | */employee/organization/{organizationId}* |
`POST` | */employee/* |

### Organization Rest API

Method | URI | Description | Parameters |
--- | --- | --- | --- |
`GET` | */organization/* |
`GET` | */organization/{id}* |
`GET` | */organization/{id}/with-departments* |
`GET` | */organization/{id}/with-departments-and-employees* |
`GET` | */organization/{id}/with-employees* |
`POST` | */organization/* |

### Department Rest API

Method | URI | Description | Parameters |
--- | --- | --- | --- |
`GET` | */department/* |
`GET` | */department/{id}* |
`GET` | */department/organization/{organizationId}* |
`GET` | */department/organization/{organizationId}/with-employees* |
`POST` | */department/* |
