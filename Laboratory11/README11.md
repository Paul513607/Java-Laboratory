# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory11
__REST Services__
Continue the application created at the previous lab integrating the following functionalities: <br />

Implement REST services needed to comunicate with the social network data (CRUD). <br />
The main specifications of the application are: <br /> <br />

# Compulsory11
Create a Spring Boot project containing the REST services for comunicating with the database. <br />
Create a REST controller containing methods for: <br />
obtaining the list of the persons, via a HTTP GET request. <br />
adding a new person, via a HTTP POST request. <br />
modifying the name of a person, via a HTTP PUT request. <br />
deleting a person, via a HTTP DELETE request. <br />
Test your services using the browser and/or Postman. <br /> <br />

We create a Spring Boot application which we connect to a Postgres Database. We create the elements of the MVCS classes for a user: UserEntity, UserDto for model, UserRepository for extracting users from the database, UserService for modelling the extracted data and UserController for implementing the REST API. We also create a ControllerAdvisor for exceptions. <br />
We implement the mentioned methods for our REST API and test their functionality with Postman. <br /> <br />


# Homework11
Create REST services for inserting and reading relationships. <br />
Create a service for determining the first k most popular persons in the network. <br />
Create a simple client application that invokes the services above, using the support offered by Spring Boot. <br />
Document your services using Swagger or a similar tool. <br />
(+1p) Secure your services using the HTTPS protocol and JSON Web Tokens <br /> <br />



# Bonus11
Write a service that determines in linear time all persons who are so important to the social network such that, if one of them were eliminated, the network would become disconnected. <br />
Create a simple desktop application that sends multiple concurrent invocations to the service above, in order to determine how many API requests per minute your service can handle. <br />
You may also monitor other performance metrics, using your own implementation or Spring support. <br /> <br />



