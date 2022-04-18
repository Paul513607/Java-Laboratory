# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory8
__JDBC - World Cities__
Write an application that allows to connect to a relational database by using JDBC, submit SQL statements and display the results. <br /> <br />

# Compulsory8
Create a relational database using any RDBMS (Oracle, Postgres, MySql, Java DB, etc.). <br />
Write an SQL script that will create the following tables: <br />
* countries: id, name, code, continent <br />
* continents: id, name <br />
Update pom.xml, in order to add the database driver to the project libraries. <br />
Create a singleton class in order to manage a connection to the database. <br />
Create DAO classes that offer methods for creating countries and continents, and finding them by their ids and names; <br />
Implement a simple test using your classes. <br /> <br />

We create the database Cities using Postgresql. This database has the following tables: continents, countries, cities. <br />
We write the sql script that creates the specified tables with the specified attributes. <br />
We create the singleton class Database, in order to manage our database connections (the __getInstance__ method will return the created instance of __Connection__). <br />
We create DAOs for Countries and Continents, implementing methods such as create, findById, findByName, findAll, and for CountriesDao findCountriesOnContinent method. <br />
We create a simple test which adds a continent and two countries on that continent to the database, then extract them, do some assertions, and print them. <br /> <br />

# Homework8
Create the necessary table in order to store cities in your database. A city may contain: id, country, name, capital(0/1), latitude, longitude <br />
Create an object-oriented model of the data managed by the application. <br />
Create a tool to import data from a real dataset: World capitals gps or other. <br />
Display the distances between various cities in the world. <br />
(+1p) Create a 2D map (using Swing or JavaFX) and draw on it the cities at their corresponding locations. <br /> <br />

We create the table __cities__ with the specified attributed in order to store cities. <br />
The model of the application will have a generic MapObject with name and id, and model classes for Continent, Country, City that extend the MapObject. We also have afferent the ContinentDao, CountryDao and CityDao classes. <br />
We create the class DatasetImported in the package util which imports data from real datasets of continents, countries and cities around the world and adds the data to the databbase. <br />
Inside the CityDao class we create a method that takes the ids of two cities and calculates the distance between them based on the query-returned latitudes and longitudes.  <br />
We create a 2D map using JavaFX on which we will draw the capitals in their locations on the map. To calculate the specified coordinates we use a Mercator object (EllipticalMercator), convert the coordines such that they fit in our map and place them accordingly. <br /> <br />



# Bonus8
Use a connection pool in order to manage database connections, such as C3PO, HikariCP or Apache Commons DBCP. <br />
Two cities are sisters (or twins) if they have a form of legal or social agreement between for the purpose of promoting cultural and commercial ties. <br />
Using a ThreadPoolExecutor create and insert into your database a large number of fake cities (≥1000) and random sister relationships among them (the sisterhood probability should be low). <br />
Using Bron Kerbosch algorithm determine the sets of cities (inclusionwise maximal, with at least 3 elements) that are all sisters with each other. <br /> <br />

Using C3PO we create the class DatabaseConPool which uses a ComboPooledDataSource to manage the connections to the database. This class is also a singleton. <br />
We create the table city_sister_relation for CitySisterRelation objects. The elements in this table have two foreign keys to the city table. We also create the model class CitySisterRelation and the dao CitySisterRelationDao for adding such relations to the database. <br />
Using a ThreadPoolExecutor inside the class FakeCityGenerator (package: util), we generate around 1000 fake cities, and add random city sister relations between them (the probability of adding such relations is low).  <br />
We create the class CityRelationsGraph with a set of City objects (nodes) and a set of CitySisterRelation objects (edges). This class implements JGraphT's Graph interface. To populate the graph we get all the citySisterRelations from the city_sister_relation table, add them as edges and for each cityId in each relation, we query the cities table for that id and add the returned City objects. <br />
Finally, we apply __BronKerboschCliqueFinder algorithm__ on the graph, and we print the found cliques with at least 3 nodes in them. <br /> <br />



