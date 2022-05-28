package classinfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFileWalker {
    private MyClassLoader myClassLoader = new MyClassLoader();

    public void getAllClasses(String path) throws IOException {
        if (path.endsWith(".jar")) {
            walkJar(path);
        } else if (path.contains("/src/main/java/")) {
            walkAndCompile(path);
            path = path.substring(0, path.indexOf("src")) + "target/classes/";
            walkDir(path);
        } else {
            walkDir(path);
        }
    }

    public void walkJar(String path) throws IOException {
        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + path + "!/")};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }

            String className = je.getName().substring(0, je.getName().length() - ".class".length());
            className = className.replace('/', '.');

            Class<?> cls = null;
            try {
                cls = classLoader.loadClass(className);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            if (cls != null) {
                myClassLoader.printClassInfo(cls);
                myClassLoader.invokeTestMethodsOfClass(cls);
            }
        }
    }

    public void walkDir(String path) throws IOException {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File file : list) {
            if (file.isDirectory()) {
                walkDir(file.getAbsolutePath());
                // System.out.println("Directory:" + file.getAbsoluteFile());
            } else if (file.getPath().endsWith(".class")) {
                myClassLoader.invokeLoadClass(file.getAbsolutePath());
            }
        }
    }

    private void walkAndCompile(String path) throws IOException {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File file : list) {
            if (file.isDirectory()) {
                walkDir(file.getAbsolutePath());
                // System.out.println("Directory:" + file.getAbsoluteFile());
            } else if (file.getPath().endsWith(".java")) {
                String pathToClass = file.getAbsolutePath();
                MyClassLoader.compileFile(pathToClass);
                pathToClass = pathToClass.replace(".java", ".class");
                String initPath = pathToClass.substring(0, pathToClass.indexOf("src"));
                String endPath = pathToClass.substring(pathToClass.indexOf("java/") + "java/".length());
                String fullPath = initPath + "target/classes/" + endPath;
                Files.move(Path.of(pathToClass), Path.of(fullPath), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
