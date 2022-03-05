# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory3

# Compulsory3
The Network Routing Problem <br />
A network contains various nodes, such as computers, routers, switches, etc. Nodes in the network have unique names and may have other common properties such as hardware (MAC) address, the location on a map, etc.
Some of them are identified using an IP-address. Some of them are able to store data, having a storage capacity, expressed in gigabytes (GB).
The time (measured in milliseconds) required for a network packet to go from one node to another is known. <br />
Computers and routers are identified by IPs, while only computers are able to store data. <br />
Create an object-oriented model of the problem. You should have at least the following classes Network, Node, Computer, Router, Switch. The natural ordering of the nodes is given by their names. <br />
Create the interfaces Identifiable and Storage. The classes above must implement these interfaces accordingly. <br />
The Network class will contain a List of nodes. <br />
Create and print all the nodes in the network (without the time costs). <br /> <br />

We create two interfaces, Identifiable and Storage.  <br />
We create the abstract class of Node. <br />
Afterwards, we create three classes: Computer, Router, Switch. All of them extent the class Node, while Computer and Router implement Identifiable (since they have ipAdresses) and Computer implements Storage as well (since it has a storageCapacity). <br />
We create the NetworkEdge class, which designs an edge between two nodes (identified by their names), along with it's time cost. <br />
We create the Network class, which contains a list of nodes and a list of edges. We will not allow for the same node (identified by name) to be put in twice, as well as check that each edge's src and dest nodes are already in out list of nodes. We print the nodeList. <br />

# Optional3
Each node will contain a Map representing the time costs. Create and print the complete network in the example. <br />
Create a default method in the interface Storage, that is able to return the storage capacity in other units of storage (megabyte, kilobyte, byte). <br />
In the Network class, create a method to display the nodes that are identifiable, sorted by their addresses. <br />
Implement an efficient agorithm to determine all the shortests times required for data packets to travel from an identifiable node to another. <br /> <br />

For each node we create a map which holds pairs of nodes with whom it has links with as well as the timeCost of those links. Since a link goes both ways, when we want to add a link from v1 to v2, we add it from v2 to v1 as well. <br />
We create a default method in the storage class which takes in a BytesUnit (which is an enum object) and it prints an error message, since it doesn't have a storageCapacity attribute. In the Computer class we override this method, returning the suitable conversion for the storageCapacity depending on the BytesUnit (initially, the storageCapacity is in GB). <br />
We iterate through the nodeList and using the operator 'instanceOf' we find which of the nodes are Identifiable. We add those to a list, we sort it by ipAdresses and we print it. <br />
To display the network schema, we print the nodes then we iterate through their linksTimeCosts and we print the links and the costs. <br />
The way we get the minimum cost from a node to the others is with Dijkstra's algorithm, since the links and the nodes form a graph, and we can use it since the time costs can't be of negative value. We initialize the costs from the first node to the others (putting INFINITY -- Integer.MAX_VALUE when we don't have a direct path) and we visit the first node. While there are still unvisited nodes, we get the one whose path is smallest, we go through it's neighbors and update the minimum path cost if necessary. In the end we'll have the time costs from the startingNode to all the other nodes. <br />

# Bonus3
