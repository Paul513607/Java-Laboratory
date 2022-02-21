# Java-Laboratory
 Homework and Projects from the Java Laboratory

Name: Samson Ioan-Paul
Group: 2A6

# Laboratory1

# Compulsory1
Write a Java application that implements the following operations:
Display on the screen the message "Hello World!". Run the application. If it works, go to step 2 :)
Define an array of strings languages, containing {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"}
Generate a random integer n: int n = (int) (Math.random() * 1_000_000);
Compute the result obtained after performing the following calculations:
  multiply n by 3;
  add the binary number 10101 to the result;
  add the hexadecimal number FF to the result;
  multiply the result by 6;
Compute the sum of the digits in the result obtained in the previous step. This is the new result. While the new result has more than one digit, continue to sum the digits of the result.
Display on the screen the message: "Willy-nilly, this semester I will learn " + programmingLanguages[result].

We display the message on the screen and declare the string array in the main. Afterwards, the function getResult returns the random number, multiplied by 3, summed by '10101' and 'FF', and multiplied by 6. The function sumDigits sums up the digits of the given number, and the function controlNumber calculates the 'control digit' of the given number. Afterwards, we diplay the last message.

# Homework1