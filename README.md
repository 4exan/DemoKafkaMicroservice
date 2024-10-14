# Demo social network project

## Description
Twitter-like demo project created using microservice architecture to decompose different task on differen services. Also in this project i tried to implement Message Broker, in my case it was Apache Kafka.

## Used tools/technologies
|Category|Tool/Technology|
|-|-|
|Languages|![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)|
|Backend|![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)|
|Frontend|![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) ![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)|
|Database|![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)|
|Services & Tools|![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)|
|IDE & Editors| ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) ![Neovim](https://img.shields.io/badge/NeoVim-%2357A143.svg?&style=for-the-badge&logo=neovim&logoColor=white)|
|OS|![Arch](https://img.shields.io/badge/Arch%20Linux-1793D1?logo=arch-linux&logoColor=fff&style=for-the-badge)|

## Overview
Main goals of this project was to try myself in microservice architecture. I tried to implement all features that i recently learn:
- **JWT token:** Already been used in previous project [Delivery service](https://github.com/4exan/DeliveryService). But its been used in context of single page app. Here JWT is implemented as filter in api-gateway;
- **Apache Kafka:** I tried to implement message broker to have more secure connection in weak spots as post, like, and follow services;
- **Redis Cache:** On api-gateway i implement connection to Redis DB that can cache created tokens, to reduce load on auth-service;
- **Docker:** I tried to conterise all services and launch them in separate containers rather than in one machine. But at the moment _(14.10.2024)_ i successfully failed this task cuz of 2 problems:
  1) I dont know how to properly conterise frontend part.
  2) Have some problemos with connection on kafka containers.

So i leave this project for good days. When i figure out how to properly make containers and finish project.

## Microservice overview
In this project pressent 14 total modules:
|№|Service name|Service URL|Port|Related services|Description|
|-|-|-|-|-|-|
|1|server-registry| / | :8761 | All | Eureka Server resolver|
|2|gateway| redis | :8765 | All | Api gateway, redirects all requests to proper services and filter them thru auth-service|
|3|frontend| / | :5731 | All | Frontend part of project|
|4|auth-service| /auth/.. | :8080 | postgres | Contains all security logic: user login, user registration, token creation and validation|
|5|user-service| /user/.. | :8081 | postgres | Contains all logic relates to user: get your profile, get other user profile, edit your profile and other|
|6|post-service| /post/.. | :8082 | post-producer / post-consumer | Contains all logic related to posts: get user posts, get liked posts, delete post etc.|
|7|like-service| /like/.. | :8083 | like-producer / like-consumer | Contains all logic related to likes: get user likes remove like etc.|
|8|follow-service| /follow/.. | :8084 | follow-producer / follow-consumer | Contains all logic related to follows: get user followers, ger who user followed etc.|
|9|post-producer| /post-create/.. | :8090 | kafka | Send message with post payload to kafka server in 'posts' topic |
|10|post-consumer| - | :8091 | kafka / postgres | Listen to 'posts' topic and save all new messages|
|11|like-producer| /like-create/.. | :8092 | kafka | Send message with like payload to kafka server in 'likes' topic |
|12|like-consumer| - | :8093 | kafka / postgres | Listen to 'likes' topic and save all new messages|
|13|follow-producer| /follow-create/.. | :8094 | kafka | Send message with follow payload to kafka server in 'follow' topic |
|14|follow-consumer| - | :8095 | kafka / postgres | Listen to 'follow' topic and save all new messages|

## Application design
Main design concept is to make app prepared to expand in peak perfomance time. To make it simpler: If many people use this app so very high posibility for post, like and follow services to drop(by drop i mean slow perfomance too, not only downed server) due to high load. So i created 6 different servicer - 3 producer and 3 consumer and connect them thru Apache Kafka. So in theory if i can properly dockerize this servers and with Kubernetes i can launch as many containers as i need.

Here is application diagram that i made:
![image](https://github.com/user-attachments/assets/2ebda8a6-a098-4676-b3a9-abc0bf1382d3)


## Screenshots
![image](https://github.com/user-attachments/assets/a54c8e0e-cfe9-47ef-8ebc-d802cfe35854)
