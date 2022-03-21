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
load: loads the catalog from an external file. <br />

We create an abstract class Item which has id, title, location and a map for tags. We create two classes which extent it, Book and Article, which have some extra attributes. <br />
We create a Catalog class, which contains a list of Items. We implement methods like add (adds an item, making sure it's id didn't appear before), findItemById, and toString. <br />
We create a CatalogUtil class, which has two static methods, save, which saves a catalog to a file (both given as parameters) as a JSON, and a load, which takes the JSON of a file (given as parameter) and create a Catalog object (if possible). <br />
We also create custom exceptions like SameIdItemExistsException and InvalidCatalogException, which help us throw more specific exceptions for out problem if errors occur. <br />

# Homework5

# Bonus5