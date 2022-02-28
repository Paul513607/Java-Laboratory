# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory2

# Compulsory2
An instance of the Room Assignment Problem consists of events and rooms. Events may be courses, labs, seminars, etc. Rooms may be lecture halls, computer labs, etc. <br />
Each event has a name, a number of participants (its size), a start time and an end time. <br />
Each room has a name, a type and a capacity. <br />
We consider the problem of assigning a room to each event such that the constraints are satisfied and the number of used rooms is as small as possible (if possible). <br />
Create an object-oriented model of the problem. You should have (at least) the following classes: Event, Room. <br />
The start and end time will be integers. The rooms will also have the property type. The available types will be implemented as an enum . <br />
Each class should have appropriate constructors, getters and setters. <br />
The toString method form the Object class must be properly overridden for all the classes. <br />
Create and print on the screen the objects in the example. <br />

We create two classes, Event and Room, as well as two enums, EventType and RoomType, which contain the available types for each of the two classes respectively. <br />
The Event class has a name, which is actually an EventType, a capacity (int), and a startTime and an endTime, which are LocalTime objects. <br />
The Room class has a name (String), a type (RoomType) and a capacity (int). <br />
Each of the classes has the appropiate constructors, and getters and setters for each attribute. <br />
The method toString is overrited appropiately in both classes, and in order to print the type of a certain Event/Room we use a switch statement and print the string corresponding to that specific EventType/RoomType respectively. <br />

# Homework2


# Bonus2