# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory9
__Persistence__ <br />
Continue the application from lab 8, creating an object-oriented model and using JPA (Java Persistence API) in order to communicate with the relational database. <br /> <br />

# Compulsory9
Create a persistence unit (use EclipseLink or Hibernate or other JPA implementation). <br />
Verify the presence of the persistence.xml file in your project. Make sure that the driver for EclipseLink or Hibernate was added to to your project classpath (or add it yourself). <br />
Define the entity classes for your model (at least one) and put them in a dedicated package. You may use the IDE support in order to generate entity classes from database tables. <br />
Create a singleton responsible with the management of an EntityManagerFactory object. <br />
Define repository clases for your entities (at least one). They must contain the following methods: <br />
create - receives an entity and saves it into the database; <br />
findById - returns an entity based on its primary key; <br />
findByName - returns a list of entities that match a given name pattern. <br />
Use a named query in order to implement this method. <br />
Test your application. <br /> <br />

We create a Hibernate persistence unit, as well as the 'persistence.xml' file from a template. <br />
The model is formed out of the following classes: Continent, Country, City. <br />
We create the singleton class EntityManagerCreator, which manages an EntityManagerFactory object. <br />
We create the following repositories: ContinentRepository, CountryRepository, CityRepository and use an EntityManager object to implement the functions for the NamedQueries in the model (like create, findByName, findById). <br />
We write two tests, first one testing just the model with JPA, and the second one testing the EntityManagerCreator singleton and the repositories. <br /> <br />

# Homework9

# Bonus9