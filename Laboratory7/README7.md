# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory7
__Concurrency__
Write a program that simulates a word game between a given number of players. <br />

At the beginning of the game there is a bag containing a number of tiles. Each tile represents a letter and has a number of points. The application will also use a dictionary that contains a list of acceptable words. Each player extracts 7 tiles from the bag and must create a word from the dictionary with them. When a player creates a word, it submits it to the board and receives a number of points (the value of tile points multiplied by the length of the word). After submitting a word of length k, the player will immediately request other tiles k from the bag(if this is possible). If the player cannot create a word, it will discard all the tiles and extract others (and passes its turn). The game ends when the bag becomes empty. The winner is the player with the highest score. <br />
The players might take turns (or not...) and a time limit might be imposed (or not...). <br /> <br />

# Compulsory7
Create an object oriented model of the problem. You may assume that there are 10 tiles for each letter in the alphabet and each letter is worth a random number of points (between 1 and 10). <br />
Each player will have a name and they must perform in a concurrent manner, extracting repeatedly tokens from the board. <br />
After each extraction, the player will submit to the board all the letters. <br />
Simulate the game using a thread for each player. <br />
Pay attention to the synchronization of the threads when extracting tokens from the bag and when putting words on the board. <br /> <br />

We create the following classes for the game: Tile (is a pair of letter-points), Bag (contains a list of tiles from which the players will be extracting), Board (cointains a list of words the players submit and prints them to the screen), Player (a class which implements the Runnable interface, so we can start multiple players at once. Players will extract tiles from the Bag and submit them to the Board), Game (contains the main method and manages the addition of players and the starting of the game). The Bag starts with 10 tiles of each letter from 'A' to 'Z', each worth a random number of points (between 1 and 10). In order to avoid race-conditions, the methods where we add/remove things to and from collections (like extractTiles and addWord) will be synchronized. After each extraction, the player concatenes the letters of the extracted tiles and sumbits the word to the board.


# Homework7
Use the following number of tiles for each letter: A-9, B-2, C-2, D-4, E-12, F-2, G-3, H-2, I-9, J-1, K-1, L-4, M-2, N-6, O-8, P-2, Q-1, R-6, S-4, T-6, U-4, V-2, W-2, X-1, Y-2, Z-1 <br />
Use the following points for the letters: <br />
* (1 point)-A, E, I, O, U, L, N, S, T, R <br />
* (2 points)-D, G <br />
* (3 points)-B, C, M, P <br />
* (4 points)-F, H, V, W, Y <br />
* (5 points)-K <br />
* (8 points)- J, X <br />
* (10 points)-Q, Z <br />
Create an implementation of a dictionary, using some collection of words. Use an appropriate collection to represent the dictionary. This collection should be large enough; you may use aspell to generate a text file containing English words, or anything similar: WordNet, dexonline, etc. <br />
Implement the scoring and determine who the winner is at the end of the game. <br />
Make sure that players wait their turns, using a wait-notify approach.
Implement a timekeeper thread that runs concurrently with the player threads, as a daemon. This thread will display the running time of the game and it will stop the game if it exceeds a certain time limit. <br /> <br />



# Bonus7
The dictionay must offer the possibility to search not only for a word, but for words which start with a given prefix (lookup). <br />
Implement the prefix search (for a classical collection) using a multi-threaded approach (parallel streams, ForkJoin, etc). <br />
Represent the words in the dictionary as a prefix tree or directed acyclic word graph for memory efficiency and prefix lookups. <br />
Compare the running time for the lookup operation between a standard collection and the one above (prefix tree or dawg). <br />

