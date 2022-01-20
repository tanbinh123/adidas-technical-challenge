# Adidas Technical Challenge

Microservice Application | Adidas Technical Challenge.

![impossibleisnothing](https://user-images.githubusercontent.com/50203409/150191898-71370777-e21f-42b9-97be-821573647695.jpeg)

## Table Of Contents

* [Description](#description-anchor)
* [Getting Started](#getting-started-anchor)
    * [Dependencies](#dependencies-anchor)
    * [Installing](#installing-anchor)
    * [Executing program](#executing-anchor)
* [Remote Files](#remote-anchor)
* [Libraries Used](#libraries-anchor)
* [CI/CD Pipeline](#pipeline-anchor)
* [Api Swagger](#swagger-anchor)
* [Problems Faced](#problems-anchor)
* [The End](#end-anchor)

<a name="description-anchor"></a>
## Description 

For this technical challenge I had to create a microservice application following this structure:

![imagen](https://user-images.githubusercontent.com/50203409/150194675-571a6522-5cff-4ecd-82aa-c5c493dbedf0.png)

The point was to set up a newsletter subscription application with three independent microservices that would be linked together:

* <b>Public Service:</b> The main service that connects to the frontend application, based on this site: 

![imagen](https://user-images.githubusercontent.com/50203409/150195251-23a2cca8-9a76-4508-b1c5-65cef00599f7.png)

The job of this service is to redirect the received request to the <b>Subscription Service</b>, which has the logic to persist data and connect to the <b>Email Service</b> to send an email to the user.

* <b>Subscribe Service:</b> Securized service that has 4 functions; subscribe a new user, unsubscribe a user, get a subscription data and get all subscriptions data. It also connects to <b>Email Service</b>, sending the subscription data so it can build and dispatch an email to the subscribed user.

* <b>Email Service:</b> Its only purpose is to send an email to the subscribed user.

<a name="getting-started-anchor"></a>
## Getting Started

<a name="dependencies-anchor"></a>
### Dependencies

Software needed to run this application:

* Java 11
* Maven
* Docker
* PostgreSQL
* MailHog

<a name="installing-anchor"></a>
### Installing

* Clone this repository to your machine using this command:

  ```
  git clone https://github.com/csantos92/adidas-technical-challenge.git
  ```
 
* Install PostgreSQL:

  ```
  https://www.postgresql.org/download/
  ```
  
  Once installed, execute these queries to set up the database or do it manually using pgAdmin GUI (don't forget to grant all privileges to the created user 'adidas_app' or it won't connect to the Subscription Service):
  
  ```
   /* CREATE DATABASE */
   CREATE DATABASE adidas;

   /* CREATE USER AND SCHEMA */
   CREATE USER adidas_app WITH ENCRYPTED PASSWORD 'adidas_app';
   CREATE SCHEMA adidas;
   GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA adidas TO adidas_app;

   /* CREATE TABLE */
   CREATE TABLE adidas.tb_adidas_subscription (
      id SERIAL PRIMARY KEY,
      email VARCHAR(30) NOT NULL,
      name VARCHAR(30),
      gender VARCHAR(6),
      birthdate VARCHAR(10) NOT NULL,
      consent BOOLEAN NOT NULL
   );
  ```
  
  ```
  /* INSERT MOCK DATA TO TEST METHODS */
  INSERT INTO "adidas"."tb_adidas_subscription" ("id", "email", "name", "gender", "birthdate", "consent") VALUES (1, 'test@test.com', 'test', 'female', '20/06/1992',   true);
  ```
  
* Set up a Kafka Server, you can use this docker command which downloads an image that it's already configurated:

  ```
  docker run -p 9092:9092 -d bashj79/kafka-kraft
  ```

* Download and execute MailHog, used to get mock emails:

  ```
  https://github.com/mailhog/MailHog/releases
  ```

<a name="executing-anchor"></a>
### Executing program

* Setting the application up

  You have to open a terminal for each service folder and run these 2 commands:

  ```
  mvn clean install
  ``` 
  
  ```
  mvn spring-boot:run
  ``` 

  Run all services in this order: Config-Server, Discovery-Service, Api-Gateway-Service, Public-Service, Subscribe-Service, Email-Service.
  
  If you want to test Subscription Service API Rest methods without security replace this lines in class <b>SubscriptionServiceApplication.java</b>.
  
  ```
  @SpringBootApplication
  ```
  To
  ```
  @SpringBootApplication ( exclude = {SecurityAutoConfiguration.class} )
  ```

  Now the application should be ready.

* <b>How it works</b>

  First, you can follow this link to check the API Swagger Documentation: [API Documentation](https://app.swaggerhub.com/apis/csantos92/adidas-technical-challenge/1.0.0#/)
  
  * <b>Public Service localhost:8080/adidas/subscribe</b>
    
     Using this endpoint will subscribe a user, according to the body JSON that we write:
    
     ![public - subscribe](https://user-images.githubusercontent.com/50203409/150201108-cec385ed-46ff-4f4b-a895-77115252cdd2.png)
  
     This service will send a message (Kafka topic) with the object to the Kafka Server, then the Subscription Service, which is listening to that topic, will receive the object. Then it will save the data into the database and send a message (Kafka topic) that will be delivered to Email Service, and then an email will be send to the user subscribed inbox:
    
     ![database1](https://user-images.githubusercontent.com/50203409/150201614-a55d040c-9541-4878-8708-a69ce26ce804.png)

     ![public-service-email1](https://user-images.githubusercontent.com/50203409/150201621-450daa4a-3c65-4b06-b99e-53deef50213d.png)

     ![public-service-email2](https://user-images.githubusercontent.com/50203409/150201634-902f5f4c-7bb5-44d4-8a15-3db6c841b1cb.png)

  * <b>Subscription Service localhost:8092/subscribe</b>

     This endpoint does the same as the previous one, but it will call the Subscription Service directly. As this is a secured service (if we don't deactivate it)       credentials will be needed as we will see later. Example with security deactivated:
    
     ![scured-subscribe1](https://user-images.githubusercontent.com/50203409/150202527-344fc36c-3816-44c6-9a65-ea71f94778f6.png)

     ![database2](https://user-images.githubusercontent.com/50203409/150202541-113e09a6-3ab4-485f-92c8-041ea395e3b2.png)

     ![secured-service-email1](https://user-images.githubusercontent.com/50203409/150202547-70dc7ea6-7cf5-4c9e-9132-32ce0a2d361e.png)
    
     ![secured-service-email2](https://user-images.githubusercontent.com/50203409/150202557-7417e93b-81f7-408b-8c88-2a2de4ebd77b.png)

  * <b>Subscription Service localhost:8092/unsubscribe/{id}</b> 
    
     This endpoint cancels an existing subscription, changing the flag content to false and saving it into the database:
    
     ![secured-cancelled](https://user-images.githubusercontent.com/50203409/150202948-f7642be9-780f-4e50-86a0-e6417dc47f7d.png)

     ![secured-cancelled2](https://user-images.githubusercontent.com/50203409/150202970-ea0def87-7a4a-45f3-a93f-ad3c30bfc092.png)

  * <b>Subscription Service localhost:8092/subscription/{id}</b>
    
     This endpoint gets a subcription data by its ID, if it doesn't exist it will return an error:
   
     ![secured-getsub](https://user-images.githubusercontent.com/50203409/150203199-0b097953-8617-4df9-89f2-6bafe4a05e7f.png)
   
   * <b>Subscription Service localhost:8092/subscriptions</b>
    
     This endpoint gets all subscriptions:
   
     ![secured-getsubs](https://user-images.githubusercontent.com/50203409/150203318-9c29028a-bb28-4c12-a3a6-7318c0624ba7.png)

   * <b>JSON body validations</b>

     Validations are made to check that the JSON object is valid for create a new subscription:
    
     ![validations](https://user-images.githubusercontent.com/50203409/150203458-82c89fe9-29b1-4b6e-b3bd-ad6c6d944984.png)
    
   * <b>Securized Subscription Service</b>
   
     When security is activated it's necessary to introduce credentials (user and pass are set in subscription-service config file):
     
     ![secured-getsub-noauth](https://user-images.githubusercontent.com/50203409/150206124-1a0823eb-01fd-4b98-a595-879b278736bd.png)

     ![secured-getsub-auth](https://user-images.githubusercontent.com/50203409/150206143-18b24309-c20d-41ec-bedf-5931e6542e2f.png)

    

<a name="remote-anchor"></a>
## Remote Files

The configurations of all services are placed in another respository, you can check it following this link [Config Repository](https://github.com/csantos92/adidas-technical-challenge-config). I've done it this way to have all configurations files centralized, that's why I created the Config-Server, which connects the rest of the services to that repository.

<a name="libraries-anchor"></a>
## Frameworks And Libraries

For this project I've used the next frameworks/libraries:

   * <b>Spring Boot</b>
      * Java framework that permits us create standalone applications with simple configurations. I used this framework because of its potential to create microservice applications.
   
   * <b>Spring Boot Web</b>
      * Dependency that permits to create RESTful applications. I used it to implement REST methods.
   
   * <b>Spring Boot Config Server/Client</b>
      * Dependency that allows to create an application that serves as a link to other services to use config files remotely. Config Server is the service that links all the other services to config files in my repository.
   
   * <b>Spring Boot JPA</b>
      * Dependency that connects to database in an efficiently way. I used it to map an entity object with a database table and enable CRUD.
   
   * <b>Spring Boot Security</b>
      * Dependency that autoconfigures security to the application. I used it to securize Subscription Service.
   
   * <b>Spring Boot Mail</b>
      * Dependency that allows to send emails. I used it in Email Service. 
   
   * <b>Netflix Eureka Server/Client</b>
      * Server that permits applications to register and connect each other. I used it in Discovery Service.
   
   * <b>Netflix Zuul</b>
      * Serves as a proxy for external requests. I used it in Api Gateway Service to connect external requests to Public Service.
   
   * <b>Spring Boot Bootstrap</b>
      * Dependency that allows to create configuration files that will connect to remote config files.
   
   * <b>Spring Kafka</b>
      * Permits applications to communicate in a very efficiently way using topics. I used it to communicate my services.
   
   * <b>Lombok</b>
      * Dependency that gets rid of boiler plate code using annotations.
   
   * <b>Spring Boot Validation</b>
      * Dependency that allows to validate data. I used it to validata JSON body on post/put requests.
   
   * <b>JUnit</b>
      * Dependency that allows to test applications. I used it in all my tests.
   
   * <b>PostgreSQL</b>
      * Dependency that lets the application to connect to a PostgreSQL database.

<a name="pipeline-anchor"></a>
## CI/CD Pipeline

![Adidas-Technical-Challenge](https://user-images.githubusercontent.com/50203409/150207997-ae22be82-c0e0-4d3a-b590-a7f183f30836.jpg)

<a name="swagger-anchor"></a>
## Api Swagger

[Api Swagger Link](https://app.swaggerhub.com/apis/csantos92/adidas-technical-challenge/1.0.0#/)

<a name="problems-anchor"></a>
## Problems Faced

The problems I faced are all related because I didn't make it in time to implement everything:

* I couldn't set up the docker environment because of some problems with versions and connections.
* I implemented a very simple securization system to Subscribe Service because I didn't have enough time.
* I didn't try BONUS 2 challenge because of time.

<a name="end-anchor"></a>
## THE END

Thanks for taking your time in reading all of this!
