# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory5
Write an application that can manage a catalog of resources (bibliographic references), such as books, articles, etc. <br />
These resources might be represented by files in the local file system or a Web address. Apart from a unique identifier, a title and its location, a resource may have additional properties such as author(s), what year it was publihsed, description, etc. <br />

# Compulsory5
Create an object-oriented model of the problem. You should have at least the following classes: Catalog and Item. The items should have at least a unique identifier, a title and its location. Consider using an interface or an abstract class in order to describe the items in a catalog. <br />
Implement the following methods representing commands that will manage the content of the catalog: <br />
add: adds a new entry into the catalog; <br />
toString: a textual representation of the catalog; <br />
save: saves the catalog to an external file using JSON format; you may use Jackson or other library; <br />
load: loads the catalog from an external file. <br /> <br />

We create an abstract class Item which has id, title, location and a map for tags. We create two classes which extent it, Book and Article, which have some extra attributes. <br />
We create a Catalog class, which contains a list of Items. We implement methods like add (adds an item, making sure it's id didn't appear before), findItemById, and toString. <br />
We create a CatalogUtil class, which has two static methods, save, which saves a catalog to a file (both given as parameters) as a JSON, and a load, which takes the JSON of a file (given as parameter) and create a Catalog object (if possible). <br />
We also create custom exceptions like SameIdItemExistsException and InvalidCatalogException, which help us throw more specific exceptions for out problem if errors occur. <br /> <br />

# Homework5
Represent the commands using classes instead of methods. Use an interface or an abstract class in order to desribe a generic command.
Implement the commands load, list, view, report (create the classes AddCommand, ListCommand, etc.). <br />
list: prints the list of items on the screen; <br />
view: opens an item using the native operating system application (see the Desktop class); <br />
report: creates (and opens) an HTML report representing the content of the catalog. <br />
Use a template engine such as FreeMarker or Velocity, in order to create the HTML report. <br />
(+1p) Use Apache Tika in order to extract metadata from your catalog items and implement the command info in order to display them.
The application will signal invalid date or the commands that are not valid using custom exceptions. <br />
The final form of the application will be an executable JAR archive. Identify the generated archive and launch the application from the console, using the JAR. <br /> <br />

We create the interface Command, which's main method is 'execute', which every other command that implements the interface must override. <br />
We write the AddCommand (adds an item to a catalog), ListCommand (prints the catalog data), ViewCommand (opens an item's location with the default desktop app), SaveCommand and LoadCommand (as before), ReportCommand (which creates a html report of the catalog using Jackson), and an InfoCommand (which parses the json created with LoadCommand and shows metadata about it). <br />
We create custom exceptions for each command, main one being for the general CommandException. <br />
We pack all the .class's in a jar file and run it. <br /> <br />

# Bonus5
Suppose there is an official set of concepts (keywords) C, and that each item has a list of such concepts (example of such a classification system). Evolve your model in order to support this new feature.
Write an algorithm that determines: <br />
the largest set of pairs (item, concept) such that all items and all concepts in this set are distinct. <br />
the smallest set of pairs (item, concept) such that all items and all concepts are present in at least one pair. <br />
You may want to read this or even this. <br />
Create large instances of the problem and test your algorithm. <br />

For each item, we will have a list of contents (ClassificationType -- an enum). We create the class ItemGraph which implements JGraphT's Graph interface, having as nodes both Item and ClassificationType objects, and as edges ItemLink objects between an item and a type (if the type is in the item's list of contents). <br />
We use JGraphT's HopcroftKarpMaximumCardinalityBipartiteMatching algorithm in order to get the Maximum Cardinality Bipartite Matching (we define the two partitions as the items partition and the types partition). <br />
After getting the set of edges for the maximum cardinality bipartite matching, we implement the MinimumEdgeCoverAlgorithm, which will get the set of the maximum cardinality matching set, as well as Greedly picking new edges, such that we pick only unmached new edges if we can. <br />