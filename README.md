# What's inside

Spring Boot, Vue.js, Axios and Thymeleaf.

Now that you are interested, this is a boilerplate for a personal project. The boilerplate is: 

- A Spring Boot application...
- ... with Thymeleaf handling the home page to be provided...
- ... and Vue.js with Axios requesting additional data from a rest endpoint on the Spring Boot application.

## Try the latest version
The latest stable version of this app is automatically deployed to [https://spring-vue-axios-boilerplate.herokuapp.com/](https://spring-vue-axios-boilerplate.herokuapp.com/).

## Running
To run: 
```
./mvnw spring-boot:run
```

To build and run:
```
./mvnw package
 java -jar petclinic-web/target/*.jar
```

## Sample Endpoints

- `/rest/hello` is just a hello world.
- `/rest/list` returns a list of whatever to showcase the Vue.js and Axios usage at the Home Page (index.html).
