# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory6
Positional Game
Consider a game played on a grid-shaped board. A grid is a two-dimensional structure of intersecting lines, the lines are evenly spaced, intersecting at right angles. <br />
At the beginning of the game, there will be randomly placed small line-shaped tokens (sticks) that connect two adjacent intersections of the grid, along a line of the grid. <br />
An intersection that is adjacent with at least one stick, is called a node. <br />
The first player selects any node of the grid and places a circular-shaped token (stone) on it. Next, the players must alternatively choose a new unselected node that is adjacent (is connected by a stick) to the previously selected one and place a stone on it. They use stones of different colors. The player who cannot choose another node, loses the game. <br />
In order to create a graphical user interface for the game, you may use either Swing or JavaFX. <br /> <br />

# Compulsory6
The main frame of the application. <br />
A configuration panel for introducing parameters regarding the grid size and a button for creating a new game. The panel must be placed at the top part of the frame. The panel must contain at least one label and one input component. <br />
A canvas (drawing panel) for drawing the board. Draw the grid lines according to the values specified in the config panel. This panel must be placed in the center part of the frame. <br />
A control panel for managing the game. This panel will contain the buttons: Load, Save, Exit ,etc. and it will be placed at the bottom part of the frame. <br /> <br />

We split the app into 4 classes: MainFrame, ConfigPanel, ControlPanel, DrawingPanel. <br />
MainFrame class will be the main app and it will listen to mouse events.
ConfigPanel class has two spinners for setting the grid's row and column's sizes and a button for creating the grid. <br />
ControlPanel class manages buttons like EnableAI, ExportAsPNG, SaveGame, LoadGame, Help, ExitGame. <br />
DrawingPanel manages the drawing on the shapes on the window as well as recoloring of the nodes through user input. <br />
I used JavaFX in order to develop this app. <br /> <br />


# Homework6
Create the object oriented model. <br />
Initialize the game by generating random sticks and place them on the board. Implement either direct or retained mode for drawing the game board. <br />
Implement the logic of the game. When the player execute a mouse pressed operation, a stone must be drawn at the mouse location: red or blue depending on whose turn it is. Validate the move, according to the game rules. Determine the winner of the game. <br />
(+0.5p) Export the current image of the game board into a PNG file.
(+0.5p) Use object serialization in order to save and restore the current status of the game. <br /> <br />

The object oriented model is represented by a GameGraph, with GameNode(s) and GameEdge(s). The MainFrame has a GameGraph attribute and the DrawingPanel adds the nodes and edges while they're being drawn. <br />
The DrawingPanel does a __direct__ mode, drawing all the shapes to a canvas (image) and afterwards putting it on the app. <br />
The login is checked inside the MainFrame and GameGraph classes, whenever a user makes a mouse click on a stone, the GameGraph class checks if that node hasn't been used yet and if it's adjacent to the previous one. If it's valid, the DrawingPanel colours it. <br />
The winner is determined by checking wherer the possible moves for the next player is 0. <br />
In order to work with files we have a FileManager class. <br />
The FileManager class exports the game as PNG when the button is clicked by __snapshoting__ the root JavaFX node onto a WritableImage, whilst a FileChooser let's the user choose where to store the file. <br />
For serialization, we use FreeMarker. We'll serialize as JSON files, and the GameGraph, GameNode, GameEdge classes must implement Serializable. <br />
For saving, the FileManager uses a FileChooser to let the user choose where to store the save, and it uses an ObjectMapper to write the GameGraph.class as json to that path.<br />
For loading, the FileManager uses a FileChooser to let the user choose where to load the file from, and it uses an ObjectMapper to read from that file and deserialize it into a GameGraph object. That object is passed through to the MainFrame and DrawingPanel. The MainFrame stores it, and calls on DrawingPanel to draw the grid from the template GameGraph. If we could not deserialize the file into a GameGraph we the load function throws an InvalidGameGraphException. <br />
I also added functionality for a HelpButton and an ExitButton, and made an AlertBox class which creates a new scene with a given title and message. <br /> <br />


# Bonus6
Prove that the player who starts the game has always a winning strategy if and only if the corresponding graph does not have a perfect matching. <br />
Based on the above observation, implement an AI for the game. <br /> <br />
If the graph does not have a perfect matching, the stategy is: the starting player chooses a node that isn't in the maximumMatching, then the second player needs to choose the next node from an edge which isn't in the maximumMatching. Afterwards, the first player keeps going on the edges from the maximumMatching, forcing the other player to go on the edges outside it, eventually the second player will run out of moves and the first player will win. <br />
To find the Maximum Matching and whether it is a perfect matching, we make GameGraph implement JTGraph's Graph interface and we use DenseEdmondsMaximumCardinalityMatchingAlgorithm class, which applies DenseEdmondsMaximumCardinalityMatching algorithm to our graph.
For the AI, if it's either that the AI doesn't start the game or the graph has a perfect matching, we Greedyly choose the next node such that the number of possible moves for the next node is minimal (and we choose randomly if it's the first node). <br />
If in fact the AI does start and the graph doesn't have a perfect matching we follow the above strategy: we select the first node such that it isn't in any of the edges of the maximal matching, forcing the opponent to choose an edge not in the maximal matching, then we choose nodes such that we always travel on edges in the maximal matching, and eventually the opponent will run out of moves. <br />

