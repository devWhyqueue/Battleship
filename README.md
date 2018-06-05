<img src="https://s3.amazonaws.com/gs-geo-images/4aef37d9-a816-4da2-8bed-0c9aa884a4d2.png" alt="Battleship" width="60%">

The well known board game brought to another level...

## Getting Started

Check out this repo, build with maven and run it.

### Prerequisites

- Maven
- IDE (like Eclipse, IntelliJ)
- Tomcat server
- JDK 8 or higher

### Installing

To build a deployable war run:

```$ mvn clean package```

or without maven:

```$ ./mvnw clean package```

Or to start it in your IDE:

```$ mvn spring-boot:run```

During runtime it will create a folder called "bibsoft-database" which contains a h2-database.
To change the data source, just change the application properties.

Congrats, you've done it! Now launch the web application:

http://localhost:8080

## Running the tests

The project comes with standard JUnit tests for the business logic. You'll find them under:

**\src\test\java\de\queisler\battleship**

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The application framework used
* [Thymeleaf](https://www.thymeleaf.org) - The web template engine
* [Maven](https://maven.apache.org/) - Dependency Management

## Versions

Look under [battleship-releases](https://github.com/devWhyqueue/battleship/releases) for new releases.

## Authors

* **devWhyqueue** - [devWhyqueue](https://github.com/devWhyqueue)

## License

This project is licensed under the MIT License.
