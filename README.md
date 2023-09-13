## Twitter API

## Table of Contents
- [Abstract](#abstract)
- [Technologies](#technologies)
- [Endpoints](#endpoints)
- [Entity Relationship Diagram](#entity-relationship-diagram)
- [Install + Setup](#set-up)
- [Contributors](#contributors)
- [Project Specs](#project-specs)

## Abstract
Successfully accomplished this project within the Fast Track'd program through Cook Systems. Collaborated with a team to meticulously craft a RESTful API from inception, harnessing the power of Spring Boot, JPA, and PostgreSQL. This API offers comprehensive capabilities for the management of social media data, closely mirroring the conceptual model of Twitter (now referred to as X). The API encompasses data types such as User profiles, Tweets, and Hashtags.

## Technologies
- Java
- Spring Boot
- JPA
- Postman
- PGAdmin

## Endpoints
In this project I implemented the following endpoints:

GET  validate/username/exists/@{​​​​​​username}​​​​​​
GET    users/@{​​​​​​username}​​​​​​
PATCH  users/@{​​​​​​username}​​​​​​
GET    users/@{​​​​​​username}​​​​​​/tweets
POST   tweets/{​​​​​​id}​​​​​​/repost
GET    tweets/{​​​​​​id}​​​​​​/tags
GET    tweets/{​​​​​​id}​​​​​​/likes
GET    tag

## Entity Relationship Diagram
![entity](https://github.com/jhortizu01/twitter_api/assets/21073095/c66e8808-527a-4024-9cd9-33ee6bfc3477)

## Install + Setup
- Clone down repo
- Navigate to <code>src > main > java > com.cooksys.twitter_api</code>
- Run <code>TwitterApiApplication</code>

## Contributors
[Janika Hortizuela](https://github.com/jhortizu01)
[Matt Rowan](https://github.com/MRowan121)
[Michael Brill](https://github.com/ChocolateSpread)
[Taryn Orlemann](https://github.com/torlemann)

## Project Specs
The project spec & rubric can be found [here](./ProjectSpecs.md)
