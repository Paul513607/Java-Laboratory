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
Create and print all the nodes in the network (without the time costs). <br />

We create two interfaces, Identifiable and Storage.  <br />
We create the abstract class of Node. <br />
Afterwards, we create three classes: Computer, Router, Switch. All of them extent the class Node, while Computer and Router implement Identifiable (since they have ipAdresses) and Computer implements Storage as well (since it has a storageCapacity). <br />
We create the NetworkEdge class, which designs an edge between two nodes (identified by their names), along with it's time cost. <br />
We create the Network class, which contains a list of nodes and a list of edges. We will not allow for the same node (identified by name) to be put in twice, as well as check that each edge's src and dest nodes are already in out list of nodes. We print the nodeList. <br />

# Homework3

# Bonus3
