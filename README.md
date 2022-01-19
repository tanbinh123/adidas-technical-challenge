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
 
* Install PostgreSQL

  ```
  https://www.postgresql.org/download/
  ```

<a name="executing-anchor"></a>
### Executing program

* How to run the program
* Step-by-step bullets
```
code blocks for commands
```
<a name="remote-anchor"></a>
## Remote Files

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

