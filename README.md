# Mflix Web Application

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![MongoDB](https://img.shields.io/badge/-MongoDB-4DB33D?style=flat&logo=mongodb&logoColor=FFFFFF)](https://www.mongodb.com/)
[![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white)](https://spring.io/projects/spring-boot)

This is a web application on top of the MongoDB ![Sample Mflix Dataset](https://www.mongodb.com/docs/atlas/sample-data/sample-mflix/), which hold a set of movies, users and comments. The implemented features are:

* Search by id, title, year and genre

![Search](img/search.jpg?raw=true)

* Result list

![List](img/list.jpg?raw=true)

* Movie detail and random comment generation

![Movie](img/movie.jpg?raw=true)

## Prerequisites

* Java runtime 
* Git client
* Maven
* MongoDB database loaded with the ![Sample Mflix Dataset](https://www.mongodb.com/docs/atlas/sample-data/sample-mflix/)

## How to run

```
git clone https://github.com/mahurtado/MflixWebApp
cd MflixWebApp
mvn clean package
export MONGODB_URI="[your_mongodb_connection_string]/sample_mflix"
java -jar target/mflix-0.0.1-SNAPSHOT.jar
```

## License

Apache License 2.0. See the [LICENSE](LICENSE.txt) file.