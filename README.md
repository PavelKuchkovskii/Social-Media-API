<div align="center">

![e-shop-logo](userservice/src/main/resources/img/logo_readme.JPG)

<!-- Badges -->
<p>
<a>
![GitHub last commit (branch)](https://img.shields.io/github/last-commit/PavelKuchkovskii/https%3A%2F%2Fgithub.com%2FPavelKuchkovskii%2FSocial-Media-API/main)
</a>
<a>
<img alt="GitHub commit activity (branch)" src="https://img.shields.io/github/commit-activity/m/PavelKuchkovskii/Social-Media-API/develop?style=for-the-badge">
</a>
<a>
<img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/PavelKuchkovskii/Social-Media-API?style=for-the-badge">
</a>
</p>
</div>

<br />

<!-- Table of Contents -->

# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
    * [Screenshots](#camera-screenshots)
    * [Tech Stack](#space_invader-tech-stack)
    * [Features](#dart-features)
    * [Run Locally](#running-run-locally)
    * [Database structure](#key-database-structure)
- [Roadmap](#compass-roadmap)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)

<!-- About the Project -->

## :star2: About the Project

<!-- Screenshots -->

This Java RESTful API `Social Media API` application is developed for a social media platform that allows users to register, create posts, message each other, follow other users, and receive activity feeds.
Secure user access with authentication and implement authorization for different user roles. App allows users to create, edit, and delete their posts, ensuring only authorized users can perform these actions.
Implemented a follow system where users can subscribe to other users to receive their updates and activity. Provide users with a personalized activity feed, showcasing updates from the people they follow.
Project contains the API endpoints documentation.

<!-- TechStack -->

### :space_invader: Tech Stack

<details>
  <summary>Show</summary>
  <ul>
    <li><a>Java 11</a></li>
    <li><a>Spring 2.7.14 (Spring boot, Spring Security, Spring web, Spring data JPA)</a></li>
    <li><a>Maven 3.8.1</a></li>
    <li><a>Postgresql</a></li>
    <li><a>FlyWay</a></li>
    <li><a>Modelmapper 3.1.0</a></li>
    <li><a>JUnit 5</a></li>
  </ul>
</details>

<!-- Features -->

### :dart: Features

- Authentication and authorization
- Post management: Allow users to create, edit, and delete their posts, ensuring only authorized users can perform these actions.
- Enable users to send private messages to each other, fostering communication within the platform.
- Provide users with a personalized activity feed, showcasing updates from the people they follow.

<!-- Run Locally -->
### :running: Run Locally

Clone the project

```bash
  git clone https://github.com/PavelKuchkovskii/Social-Media-API.git
```

<!-- Database struct -->
### :key: Database structure

![DB_structure](src/main/resources/static/img/DB_structure.png)

<!-- Roadmap -->
## :compass: roadmap:

* [x] building a plain Application with Spring Boot
* [x] create the User controller
* [x] plug the Hibernate
* [x] connect entities with 1:M and M:M relationships
* [x] add entities validator
* [x] add DTO and converters
* [x] add global search by product name
* [x] add User registration with saving Users to database
* [x] add User authorization
* [x] create shopping cart and opportunity to add products
* [x] add orders storing to DB
* [x] display user's order history
* [x] add the ability to view the list of products of each user order
* [x] add admin page with user's list
* [x] add Spring Security with In-Memory Authentication
* [x] add Spring Security authentication form Login custom page with Database
* [x] add Spring Security roles
* [x] add Spring Security's remember me feature
* [x] add images for according products

## :handshake: Contact

Pavel Kuchkovskii - [@Pavel Kuchkovskii](https://https://www.linkedin.com/in/pavel-kuchkovskii-581400212/) - pavel.kuchkovskii@gmail.com

<!-- Acknowledgments -->

## :gem: Acknowledgements

Here are useful resources for readme writing and designing that I've used in my projects.

- [Shields.io](https://shields.io/)
- [Awesome README](https://github.com/matiassingers/awesome-readme)
- [Readme Template](https://github.com/othneildrew/Best-README-Template)
- [Technical support](https://www.baeldung.com)