import classinfo.ClassFileWalker;
import classinfo.MyClassLoader;
import javassist.*;
import javassist.bytecode.*;
import testclasses.StudentDemo;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public void testClassLoading() {
        MyClassLoader loader = new MyClassLoader();
        try {
            loader.invokeLoadClass("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory12/Compulsory12/target/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testDirOrJarTraversal() {
        ClassFileWalker fileWalker = new ClassFileWalker();
        try {
            fileWalker.getAllClasses("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory12/Compulsory12/target/classes/");
            // fileWalker.getAllClasses("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory12/Compulsory12/src/main/java/");
            // fileWalker.getAllClasses("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/out/artifacts/Compulsory5_jar/Compulsory5.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testJavassistManipulation(String pathToClass, String methodName)
            throws BadBytecode, NotFoundException, URISyntaxException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool cp = ClassPool.getDefault();
        CtClass ct = cp.get(pathToClass);

        CtMethod method = ct.getMethod("size", "()I");
        method.insertBefore("System.out.println(\"Hello world!\");");

        Class<?> cls = ct.toClass();
        ArrayList obj = (ArrayList) cls.newInstance();
        obj.size();
    }

    public static void main(String[] args) {
        Main main = new Main();
        // main.testClassLoading();
        // main.testDirOrJarTraversal();
        try {
            main.testJavassistManipulation("java.util.ArrayList", "size");
        } catch (BadBytecode | NotFoundException | URISyntaxException | CannotCompileException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
