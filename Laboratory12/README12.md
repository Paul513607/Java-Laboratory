# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory12
__Reflection__
Create an application to analyze and test java classes. <br />
The application will receive as input java classes and it will display their prototypes and perform the tests specified by the @Test annotation. <br />

The main specifications of the application are: <br /> <br />

# Compulsory12
The input will be a .class file, located anywhere in the file system. <br />
Load the specified class in memory, identifying dynamically its package. <br />
Using reflection, extract as many information about the class (at least its methods). <br />
Using reflection, invoke the static methods, with no arguments, annotated with @Test. <br /> <br />

We create the class __MyClassLoader__, in which we define a method, __loadClass__ that takes in a path to a '.class' file and, using a __ClassLoader__, finds different information about the class. For example, we print the package name of the class. <br />
Next, we define a method which prints, for a given class, different attributes. <br />
We define another method which, for a given class, checks if it has methods marked as __static__, with __no arguments__ and annotated with __@Test__, and if there are, they get called. <br /> <br />


# Homework12
The input may be a folder (containing .class files) or a .jar. You must explore it recursively. <br />
Create the complete prototype, in the same manner as javap tool.
Identify all public classes annotated with @Test and invoke the methods annotated with @Test, whether static or not. <br />
If a method requires primitive (at least int) or String arguments, generate mock values for them. <br />
Print a statistics regarding the tests. <br /> <br />




# Bonus12
Consider the case when the input files are .java files and compile the source code before analyzing them. (use Java Compiler, for example). <br />
Using additional annotations, implement non-functional tests over the methods in order to test their reliability and efficiency. <br />
Use a bytecode manipulation and analysis framework, such as ASM, BCEL or Javassist in order to extract the bytecode of the class, perform bytecode instrumentation (inject code in some method) and generate dynamically a class. <br /> <br />




