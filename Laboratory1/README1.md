# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory1

# Compulsory1
Write a Java application that implements the following operations: <br />
Display on the screen the message "Hello World!". Run the application. If it works, go to step 2 :) <br />
Define an array of strings languages, containing {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"} <br />
Generate a random integer n: int n = (int) (Math.random() * 1_000_000); <br />
Compute the result obtained after performing the following calculations: <br />
  multiply n by 3; <br />
  add the binary number 10101 to the result; <br />
  add the hexadecimal number FF to the result; <br />
  multiply the result by 6; <br />
Compute the sum of the digits in the result obtained in the previous step. This is the new result. While the new result has more than one digit, continue to sum the digits of the result. <br />
Display on the screen the message: "Willy-nilly, this semester I will learn " + programmingLanguages\[result\]. <br />

We display the message on the screen and declare the string array in the main. Afterwards, the function getResult returns the random number, multiplied by 3, summed by '10101' and 'FF', and multiplied by 6. The function sumDigits sums up the digits of the given number, and the function controlNumber calculates the 'control digit' of the given number. Afterwards, we diplay the last message. <br />

# Homework1
Let n, p be two integers and C1,...,Cm a set of letters (the alphabet), all given as a command line arguments. Validate the arguments! <br />
Create an array of n strings (called words), each word containing exactly p characters from the given alphabet. <br />
Display on the screen the generated array. <br />
Two words are neighbors if they have a common letter. <br />
Create a boolean n x n matrix, representing the adjacency relation of the words. <br />
Create a data structure (using arrays) that stores the neighbors of each word. Display this data structure on the screen. <br />
For larger n display the running time of the application in nanoseconds (DO NOT display the data structure!). Try n > 30_000. You might want to adjust the JVM Heap Space using the VM options -Xms4G -Xmx4G. <br />
Launch the application from the command line, for example: java Lab1 100 7 A C G T. <br />

For this problem we get the command line arguments, validate the input by checking if there are at least 3 given arguments, if the first (n) and second (p) arguments are integers, and the other ones are characters between 'a' and 'z' (or 'A' and 'Z'). <br />
We generate the n words by randomly choosing p characters from the third argument onwards (the characters can be repeated), then we display the array of the words on the screen. <br />
We check if two words are neighbors by mapping each letter of the first word, and then checking each letter of the second word to see if it appears in out map (this is done through the function 'areWordsNeighbors()'). <br />
Using the above mentioned function, we iterate through the array with a nested loops (2 loops), checking if the 'i'th word from the first loop is neighboring the 'j'th word from the second loop. While doing this, we put in the boolean matrix element correspoding the 'i'th and 'j'th positions the result of 'areWordsNeighbors()'. <br />
The neighborhood matrix is obtained similarily. We first create an array of arrays of size n. We iterate through the array with a nested loop (2 loops). In the first loop we create the array of the 'i'th element, and afterwards, in the second loop we check the result of 'areWordsNeighbors()', adding the 'j'th word to the 'i'th array if the result is true. We print the matrix. <br />
For the running time display, we use a startTime initialized with the current time in ns when we start the program, and an endTime initialized with the current time in ns when we end the program. The timeElapsed is the difference endTime-startTime. In order to display the matrixes only when n isn't big, we define a constant MAX_SIZE (which is, let's say, 30000), and we only print the matrixes if n isn't bigger than MAX_SIZE. <br />

# Bonus1
Implement an efficient algorithm that determines, if possible, a subset of distinct words W1,W2,...,Wk (from the ones that you have generated) such that k ≥ 3 and Wi and Wi+1 are neighbors, for all i in [1..k], where Wk+1=W1. <br />
Can you find the largest possible k? <br />

We consider the words are pre-generated, thus we won't consider the time complexity of generating them. We first generate 'buckets' for each letter of each word and a bucket 'T' will contain a set of all words that contain 'T', for example (time complexity: O(n*p) ).  <br />
Afterwards, we intersect each every pair of the sets obtained previously, to see how many words are in both; we save the intersected sets as well as the indexes of the sets we intersected (we use the class 'Inventory' which contains both an array of sets -- the intersects; and an array of pairs of integers -- pairs of indexes) ( O(26\*25/2) = O(13\*25) -- there are at most 26 letters).  <br />
The reasoning is that we want to find intersections that contain at least two elements (take, foe example, this: Set1: {AA, AT, TG}; Set2:{AA, AB, AC, TG}; The intersection: {AA, TG}. We can start with AA, go through the elements of set 2 --> {AA, AB, AC, TG} -- last one being TG, so we can go back to set 1 and go {AA, AB, AC, TG, AT}, so that we end in AT from set one which is neighbor with AA), thus guaranteeing that we can get from a set to another and back (since Wk has to be equal to W1). We also check if the final union size is >=3. ( O(26\*26\*n) - we iterate through the buckets twice, nested, and we iterate through the words once) <br />
This is the naive solution. For the better solution, we find a union with the naive algorithm and we keep intersecting that with the sets from the buckets, and we only add the bucket to our union if the intersection has at least 2 elements (same reasoning as before). This also takes O(n) time. <br />
In the end, the time complexity will be at most O( n\*p + 26\*26\*n + 26\*26 + n ) = O( n\*(p + 1 + 26 \* 26)) (We ignored the constand +26\*26).