# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory10
__Networking__ <br />
Create an application where clients connect to a server in order to form a social network. The application will contain two parts (create a project for each one): <br />

The server is responsible with the management of the clients and the implementation of the services. <br />
The client will communicate with the server, sending it commands containing the name of the service and the required parameters. The commands are: <br />
register name: adds a new person to the social network; <br />
login name: establishes a connection between the server and the client; <br />
friend name1 name2 ... namek: adds friendship relations between the person that sends the command and other persons; <br />
send message: sends a message to all friends. <br />
read: reads the messages from the server. <br /> <br />

# Compulsory10
Create the project for the server application. <br />
Implement the class responsible with the creation of a ServerSocket running at a specified port. The server will receive requests (commands) from clients and it will execute them. <br />
Create a class that will be responsible with communicating with a client Socket. The communication will be on a separate thread. If the server receives the command stop it will stop and will return to the client the respons "Server stopped", otherwise it return: "Server received the request ... ". <br />
Create the project for the client application.
A client will read commands from the keyboard and it will send them to the server. The client stops when it reads from the keyboard the string "exit". <br /> <br />

We create the project for the server application. The __Server__ class initializes a __ServerSocket__ on a specified __port__, and starts waiting for clients. When a client connects, the server creates a socket and initializes a __ClientThread__ which will read requests and send messages back to the client, until the command "stop" is given. This way the server can keep waiting for connections and start threads for each one. <br />
We create the project for the client application. The __Client__ class is a basic TCP client; it connects to the server using the __address__ and the __port__, it reads input from the console, sends it (requests) to the server, and receives messages from it. When the input is "exit" the cliend will send to the server a "stop" request, and the client will stop. <br /> <br />



# Homework10
Create an object-oriented model for your application and implement the commands. <br />
The command stop should "gracefully" stop the server - it will not accept new games but it will finish those in progress. When there are no more games, it will shutdown. <br />
Implement a timeout for a connection (a number of minutes). If the server does not receive any command from a logged in client in the specified period of time, it will terminate the connection. <br />
(+0.5p) Create a SVG representation of the social network, using Apache Batik, or other technology. <br />
(+0.5p) Upload a HTML document containing the social network representation directly from the application to a Web server. You may use JCraft for connecting to a server using SFTP and transferring a file (or a similar solution). <br /> <br />

We create the model for the application: the User model, which has a @ManyToMany relationship with itself, for the friendships modelling and the Message model, which has the following attributes: two 'id' attributes, for the user who sent and the user who received the message, the 'content' attribute and the 'wasRead' attribute. <br />
We implement the required commands and we use the Repositories for the model to add/extract data from the Database. Each command is a separate class, which implements the Command interface. <br />
For each client thread, we set a timeout on the socket. When that time is up, the server's ServerSocket will be stopped, so no more accepting user connections. Same will happend is the user sends the command 'stop'.
The server will keep count of the number of connections, and it will stop after a user sends 'stop' and all the connections have been closed. <br />
Using the class TestSVGGen we generate a SVG based on the drawing we do in Swing's Graphics2D. The drawing is based on the database data. <br />
Using the class SftpHandler we create a session on localhost, port 22 and, using the sftp protocol we upload the previously created SVG file only the server's "home". <br /> <br />


# Bonus10
Create a command that returns various properties of the social network. You may use JGraphT or other library. <br />
Using a maximum network flow algorithm, determine the structural cohesion of the network. You may want to read this (combinatorial applications of network flows). <br /> <br />

We start by creating the classes UserFriendship and UserGraph (which implements JGraptT's Graph<User, UserFriendship> interface), in order to model our social network as a graph. <br />
We implement the class ProprietiesAnalyzer, which will find cliques (groups of friends) in out social network. We implement the command "proprieties" in order for users to view such cliques. <br />
We also implement the class MaximumFlowAlgorithm, which applies JGraphT's EdmondsKarpMFImpl, in order to find the maximum flow between two users of our network. <br /> <br />




