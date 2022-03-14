# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory4

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



# Bonus4


