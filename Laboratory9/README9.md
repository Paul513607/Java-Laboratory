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
Create all entity classes and repositories. Implement properly the one-to-many relationships. <br />
Create a generic AbstractRepository using generics in order to simplify the creation of the repository classes. You may take a look at the CrudRepository interface from Spring Framework. <br />
Insert, using JPA, a large number of cities in the database and log the execution time of your JPQL queries. <br />
(+1p) Assume each city has a new property, its population. Use a constraint solver, such as Choco solver, OptaPlanner or ORTools, in order to find a set of cities having names that start with the same letter, the total sum of their population is between two given bounds and they are from different countries. <br /> <br />

We create the entity classes and repositories for the Continent, Country and City classes. <br />
We implement the one-to-many relationships by putting a list of sub-elements in the super-element, and an id of the super-element in the sub-elements. <br />
We create AbstractRepository<T, ID> based on Spring's CrudRepository in which we define basic methods like findAll, save etc. Each of our repositories will extend this AbstractRepository. <br />
We use a DatasetImporter to import a large number of continents, countries and cities from a dataset. The execution time of this operation was 1109.837651722s (seconds). <br />
We use ChocoSolver's Model and Solver classes to define a model and solve the given problem. We initialize IntVars for city populations, first letters of name, and ids (with values from the array of cities). We call solver.solve() and print the first 10 solutions. <br /> <br />

# Bonus9
Implement properly the many-to-many sisterhood relationship. <br />
Implement both the JDBC and JPA implementations and use an AbstractFactory in order to create the DAO objects (the repositories).
The application will use JDBC or JPA depending on a parameter given in an initialization file. <br />
You may also use an IoC container in order to inject the DAO implementations. <br /> <br />

We implement the many-to-many sisterhood relationship of the city class, which creates a new table, citi_sister_relationship. <br />
We implement the JDBC implementation as well.<br />
The user can specify either 'jpa' or 'jdbc' at the command line in order for the AbstractFactory to initialize it's repositories with the right implementations. <br />
We use spring's @Autowired annotation to do the dependency injection. <br /> <br />