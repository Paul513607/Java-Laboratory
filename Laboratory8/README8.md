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



# Homework8
Create the necessary table in order to store cities in your database. A city may contain: id, country, name, capital(0/1), latitude, longitude
Create an object-oriented model of the data managed by the application.
Create a tool to import data from a real dataset: World capitals gps or other. <br />
Display the distances between various cities in the world. <br />
(+1p) Create a 2D map (using Swing or JavaFX) and draw on it the cities at their corresponding locations. <br /> <br />



# Bonus8
Use a connection pool in order to manage database connections, such as C3PO, HikariCP or Apache Commons DBCP. <br />
Two cities are sisters (or twins) if they have a form of legal or social agreement between for the purpose of promoting cultural and commercial ties. <br />
Using a ThreadPoolExecutor create and insert into your database a large number of fake cities (≥1000) and random sister relationships among them (the sisterhood probability should be low). <br />
Using Bron Kerbosch algorithm determine the sets of cities (inclusionwise maximal, with at least 3 elements) that are all sisters with each other. <br /> <br />



