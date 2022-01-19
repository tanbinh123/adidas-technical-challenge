# Adidas Technical Challenge

Microservices Application for the Adidas Technical Challenge

![impossibleisnothing](https://user-images.githubusercontent.com/50203409/150191898-71370777-e21f-42b9-97be-821573647695.jpeg)

## Table Of Contents

* [Description](#description-anchor)
* [Getting Started](#getting-started-anchor)
    * [Dependencies](#dependencies-anchor)
    * [Installing](#installing-anchor)
    * [Executing program](#executing-anchor)
* [Remote Files](#remote-anchor)
* [Libraries](#libraries-anchor)
* [CI/CD Pipeline](#pipeline-anchor)
* [Api Swagger](#swagger-anchor)
* [Problems Faced](#problems-anchor)

<a name="description-anchor"></a>
## Description 

For this technical challenge I had to create a microservices application following this structure:

![imagen](https://user-images.githubusercontent.com/50203409/150194675-571a6522-5cff-4ecd-82aa-c5c493dbedf0.png)

The point was to set up a newsletter subscription application with three independent microservices that would be linked together:

* <b>Public Service:</b> The main service that connects to the frontend application, based on this site: 

![imagen](https://user-images.githubusercontent.com/50203409/150195251-23a2cca8-9a76-4508-b1c5-65cef00599f7.png)

The job of this service is to redirect the received petition to de <b>Subscription Service</b>, which has the logic to persist data and connect to the <b>Email Service</b> to send an email to the user.

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
  
  Once installed, execute these queries to set up the database or do it manually using pgAdmin GUI (don't forget to gran all privileges to the created user 'adidas_app' or it won't connect to the Subscription Service):
  
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
  
  * <b>Public Service /adidas/subscribe</b>
    
    Using this endpoint will subscribe a user, according to de body JSON that we write:
    
  ![public - subscribe](https://user-images.githubusercontent.com/50203409/150201108-cec385ed-46ff-4f4b-a895-77115252cdd2.png)
  
    This services will send a message (Kafka topic) with the object to the Kafka Server, then the Subscription Service, which is listening to that topic, will receive the object, then it will save the data into the database and send a message (Kafka topic) that will be delivered to Email Service, and then an email will be send to the user subscribed inbox:
    
    ![database1](https://user-images.githubusercontent.com/50203409/150201614-a55d040c-9541-4878-8708-a69ce26ce804.png)

    ![public-service-email1](https://user-images.githubusercontent.com/50203409/150201621-450daa4a-3c65-4b06-b99e-53deef50213d.png)

    ![public-service-email2](https://user-images.githubusercontent.com/50203409/150201634-902f5f4c-7bb5-44d4-8a15-3db6c841b1cb.png)

  * <b>Subscription Service /subscription</b>

    This endpoint does the same as the previous one, but it will call the Subscription Service directly. As this is a secured service (if we don't deactivate it) credentials will be needed as we will see later. Example with security deactivated:
    
    ![scured-subscribe1](https://user-images.githubusercontent.com/50203409/150202527-344fc36c-3816-44c6-9a65-ea71f94778f6.png)

    ![database2](https://user-images.githubusercontent.com/50203409/150202541-113e09a6-3ab4-485f-92c8-041ea395e3b2.png)

    ![secured-service-email1](https://user-images.githubusercontent.com/50203409/150202547-70dc7ea6-7cf5-4c9e-9132-32ce0a2d361e.png)
    
    ![secured-service-email2](https://user-images.githubusercontent.com/50203409/150202557-7417e93b-81f7-408b-8c88-2a2de4ebd77b.png)

  * <b>Subscription Service /unsubscribe/{id}</b> 
    
    This endpoint cancels an existing subscription, changing the flag content to false and saving it into the database:
    
    ![secured-cancelled](https://user-images.githubusercontent.com/50203409/150202948-f7642be9-780f-4e50-86a0-e6417dc47f7d.png)

    ![secured-cancelled2](https://user-images.githubusercontent.com/50203409/150202970-ea0def87-7a4a-45f3-a93f-ad3c30bfc092.png)

  * <b>Subscription Service /subscription/{id}</b>
    
    This endpoing gets a subcription data by its ID, if it doesn't exists it will return an error:
   
    ![secured-getsub](https://user-images.githubusercontent.com/50203409/150203199-0b097953-8617-4df9-89f2-6bafe4a05e7f.png)
   
   * <b>Subscription Service /subscriptions</b>
    
    This endpoing gets all subscriptions:
   
    ![secured-getsubs](https://user-images.githubusercontent.com/50203409/150203318-9c29028a-bb28-4c12-a3a6-7318c0624ba7.png)

   * <b>JSON body validations</b>

    Validations are made to check that the JSON object is valid for create a new subscription:
    
    ![validations](https://user-images.githubusercontent.com/50203409/150203458-82c89fe9-29b1-4b6e-b3bd-ad6c6d944984.png)
    
   * <b>Securized Subscription Service</b>
   

    

<a name="remote-anchor"></a>
## Remote Files

The configurations of all services are placed in another respository, you can check it our following this link [Config Repository](https://github.com/csantos92/adidas-technical-challenge-config). I've done it this way to have all configurations files centralized, that's why I created the Config-Server, which connects the rest of the services to that repository.

<a name="libraries-anchor"></a>
## Libraries

Any advise for common problems or issues.
```
command to run if program contains helper info
```
<a name="pipeline-anchor"></a>
## CI/CD Pipeline




<a name="swagger-anchor"></a>
## Api Swagger

Api Swagger Link

<a name="problems-anchor"></a>
## Problems Faced

