# Name
Reactive pet project.

## Description
There are 2 applications and a RabbitMQ between them, only communicates on that, findAllComments is with RPC.
Only one is called by FE and only the other access DB.

## Requirements
- JDK-17
- Maven 3
- Docker

## Installation
- Start RabbitMQ in Docker with 'docker run -p 15672:15672 -p 5672:5672 rabbitmq:3-management'
- Start comment-back in the folder with 'mvn clean spring-boot:run'
- Start comment-front in the folder with 'mvn clean spring-boot:run'

## Usage
Open locahost:8080 in browser, there will be comments came from DB and you can add new ones, whose are sent with server-sent events.
The tests require a running RabbitMQ and the external tester application does not delete the added comment, can be run with 'mvn test' in comment-test folder, it needs improvements.