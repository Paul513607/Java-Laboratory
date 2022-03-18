# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory4
A city hall wants to install surveillance cameras at all intersections in a city. <br />
To do this, it must connect all intersections with data cables, along the streets between them, such that the resulting network is connected.
We assume that the lengths (in km) of the streets between all the intersections of the city are known and the costs of installing the cables is proportional with the street lengths. <br />
The problem is to determine how to install the data cables (on which streets) such as the total cost is minimum. <br />

# Compulsory4

Create a Maven project. <br />
Create an object-oriented model of the problem. Streets have names and lengths, intersections have names. A street joins two intersections. <br />
Create the streets and the intersections of the problem described in the example. Use streams in order to easily create the intersections. <br />
Create a list of streets, using LinkedList implementation and sort it by the length, using a comparator expressed as a lambda-expression or method reference. Make sure all the objects are comparable. <br />
Create a set of intersections, using a HashSet implementation. Verify the property that a Set does not contain duplicates. <br />

We create the Intersection class with a name (String) attribute. <br />
We create the Street class with a name (String), length (Double), interName1 and interName2 (Strings) attributes. <br />
(We'll consider the names of the Intersections / Streets to be unique) <br />
We create a HashSet of Intersection, and we add them using the stream forEach pipeline over the range of the intersection numbers. We print it in order to check the uniqueness of the intersectins in the Set. <br />
We create a LinkedList of Streets. We sort it using a lambda which takes two streets and compares their lengths. <br />

# Homework4
Create a class that describes the city. <br />
Using Java Stream API, write a query that display all the streets that are longer than a specified value and join at least 3 streets. <br />
Use a third-party library in order to generate random fake names for intersections and streets. <br />
You may use this package of JGraphT in order to solve the problem (or other library). <br />
Note: A personal implementation of the algorithm will be will be scored extra (+1p). <br />

We create the class that represents the city, which had a set of intersections and a list of streets as attributes. <br />
We find the streets that are longer than hitLength, and that have join at least 3 other streets by filtering in the stream with the predicates:
street.getLength()>= hitLength, and by summing the number of other streets of the two intersections, and checking for >=3. <br />
We generate random names using com.github.javafaker library. <br />
We create a new class for simplicity, CityGraph, which has a set of the intersections of the city and a set of the streets of the city. This class will implement the JGraphT interface WeightedGraph, thus overriding the needed methods. In the city class we start the KruskalMSTAlgorithm on the support cityGraph, thus finding the MST for the city (the optimal way to put the data cables on the streets). <br />
We also implement Prim's MST algorithm, and give it a cityGraph (the current cityGraph) as attribute. This algorithm works since all the streets have positive lengths. <br />


# Bonus4
The city hall wants to regularly inspect the surveillance cameras, sending a maintenance car to go through it all. <br />
Implement an algorithm that determines the route of the maintenance car, in order to minimize the total length. The algorithm must run fast and should not find a solution that is twice as bad than the optimum route.
You may want to read this. <br />
Create a random problem generator, making sure that the lengths between intersections satisfy the triangle inequality. <br />

This problem is the TSP (Traveling Salesman Problem). If two intersections don't have a path between them, we add a path of length <\maxLengthOfStreets + 1>.  <br />
We define a Travel class, which holds a path through the nodes, as well as a previous path. This class can obtain new travels by swapping intersections in the current travel, and it can calculate the distance of the current travel. <br />
In order to minimize the travel length of the car, we use a Simulated Annealing Algorithm on the given cityGraph. We set the numberOfIterations to 1000, the startingTemperature to 1000.0 and the coolingRate of the temperature to 0.99. This algorithm will obtain new best travels based on their total distance from the previous best travel's distance. In case we don't find a better solution, we don't just try another one from scratch. We calculate the probability of acceptance (which is exp(bestDistance - currentBestDistance) / temperature), and if it's "big enough", we try the next travel from this one, otherwith we revert to the previous travel. <br />
In order to generate a random city we generate a random number "n" of Intersections (which is, at most, MAX_NO_INTERSECTIONS), and we generate "n" intersections, with inceasing id's and random names. We then generate random streets (at most, MAX_NO_STREETS) with random names, random lengths (which are, at most, MAX_STREET_LENGTH), and connecting two random intersections from the previously generated intersectionSet. We then check the validity of the street, i.e. that it's intersections are different, and that if there are cycles of 3 streets containing this street, they respect the triangulation rule. <br />

