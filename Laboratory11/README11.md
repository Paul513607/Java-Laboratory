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

We define a __@ManyToMany__ relationship on the table users, with which we define the friendships between them. We add controllers for adding / deleting friendships. <br />
We create the method __getTheMostPopularUsers__ in FriendshipService, in which we get all users, sort them descendingly based on the number of friends, and return as many as we need. <br />
We create a client that, using RestTemplate, makes different requests to our server, based on commands given at stdin. <br />
We add the necessary dependencies and create a bean which will enable Swing documentation. <br />
We create a https key and enable server.ssl with the key-store mapped to that. We also implement, inside __CustomAuthenticationFilter__ and __CustomAuthorizationFilter__ methods for generating and decrypting a JWT token (using auto0) respectively. We create the Configuration __SecurityConfig__ in order to allow different accesses on our endpoints (things like 'register' and 'login' (endpoint given by auth0) can be used by anyone, but for the other endpoints you need to be logged in (you need a JWT token) to make requests). <br /> <br />



# Bonus11
Write a service that determines in linear time all persons who are so important to the social network such that, if one of them were eliminated, the network would become disconnected. <br />
Create a simple desktop application that sends multiple concurrent invocations to the service above, in order to determine how many API requests per minute your service can handle. <br />
You may also monitor other performance metrics, using your own implementation or Spring support. <br /> <br />

We define a Graph model for our data: users are nodes, friendships and edges. This graph implements JGraphT's Graph interface. Thus, we can call __BlockCutpointGraph__ in order to find which persons are the most important in our netword (i.e. articulation nodes). <br />
We create a desktop application that makes a lot of requests to the server, concurrently. The results for requests / minute are: <br />

* NUMBER_OF_REQUESTS = 10^5 <br />
    - Total time taken for the requests: 0.3171728383333334min (minutes.<br />
    - Requests handled per minute: 315285.50970970857. <br />
* NUMBER_OF_REQUESTS = 10^6 <br />
    - Total time taken for the requests: 2.6550148123333335min (minutes).
<br />
    - Requests handled per minute: 376645.73295587755. <br />

Using spring's __actuator__, we define and extract different metrics about our application. <br /> <br />
