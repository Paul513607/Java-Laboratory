package classinfo;

import com.github.javafaker.Faker;
import customannotations.MockAnnotation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

@Data
@NoArgsConstructor
public class MyClassLoader {
    public void invokeLoadClass(String pathToClass) throws IOException {
        Class<?> cls = null;
        File file = new File(pathToClass);

        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};

        String classBuild;
        classBuild = pathToClass.substring(pathToClass.indexOf("classes/") + "classes/".length());
        classBuild = classBuild.replaceAll("/", ".");
        classBuild = classBuild.substring(0, classBuild.lastIndexOf('.'));

        ClassLoader classLoader = new URLClassLoader(urls);

        try {
            cls = classLoader.loadClass(classBuild);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (cls != null) {
            printClassInfo(cls);
            invokeTestMethodsOfClass(cls);
            //printInfo(cls);
            //callStaticTestMethodsWithNoParameters(cls);
        }
    }

    public void printInfo(Class<?> cls) {
        System.out.println("Name: " + cls.getName());
        System.out.println("Annotation? " + cls.isAnnotation());
        System.out.println("Interface? " + cls.isInterface());
        System.out.println("Enum? " + cls.isEnum());

        System.out.println("Superclass: " + cls.getSuperclass().getName());

        System.out.print("Constructors: ");
        Arrays.stream(cls.getConstructors()).forEach(constructor -> System.out.print(constructor.getName() + " "));
        System.out.println();

        System.out.print("Methods: ");
        Arrays.stream(cls.getMethods()).forEach(method -> System.out.print(method.getName() + " "));
        System.out.println();
    }

    public void callStaticTestMethodsWithNoParameters(Class<?> cls) {
        Arrays.stream(cls.getMethods()).forEach(method -> {
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            boolean hasNoParams = (method.getParameters().length == 0);
            boolean isTestAnnotated = method.isAnnotationPresent(Test.class);

            if (isStatic && hasNoParams && isTestAnnotated) {
                try {
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getAccessModifierName(int modifier) {
        StringBuilder accessModifierBuilder = new StringBuilder();
        if (Modifier.isStatic(modifier)) {
            accessModifierBuilder.append("static ");
        }

        if (Modifier.isFinal(modifier)) {
            accessModifierBuilder.append("final ");
        }

        if (Modifier.isSynchronized(modifier)) {
            accessModifierBuilder.append("synchronized ");
        }

        if (Modifier.isPublic(modifier)) {
            accessModifierBuilder.append("public ");
        } else if (Modifier.isProtected(modifier)) {
            accessModifierBuilder.append("protected ");
        } else if (Modifier.isPrivate(modifier)) {
            accessModifierBuilder.append("private ");
        }
        return accessModifierBuilder.toString();
    }

    private String getClassType(int modifier) {
        StringBuilder classModifier = new StringBuilder();
        if (Modifier.isAbstract(modifier)) {
            classModifier.append("abstract class ");
        } else if (Modifier.isInterface(modifier)) {
            classModifier.append("interface ");
        } else {
            classModifier.append("class ");
        }
        return classModifier.toString();
    }

    private String getFields(Class<?> cls) {
        StringBuilder classSignature = new StringBuilder();
        for (Field field : cls.getDeclaredFields()) {
            StringBuilder fieldBuilder = new StringBuilder();
            int fieldModifiers = field.getModifiers();
            fieldBuilder.append('\t');
            fieldBuilder.append(getAccessModifierName(fieldModifiers));
            fieldBuilder.append(field.getType().getSimpleName()).append(' ');
            fieldBuilder.append(field.getName());
            fieldBuilder.append(";\n");
            classSignature.append(fieldBuilder);
        }
        return classSignature.toString();
    }

    private String getConstructors(Class<?> cls) {
        StringBuilder classSignature = new StringBuilder();
        for (Constructor<?> constructor : cls.getConstructors()) {
            StringBuilder constructorBuilder = new StringBuilder();
            int constructorModifiers = constructor.getModifiers();
            constructorBuilder.append('\t');
            constructorBuilder.append(getAccessModifierName(constructorModifiers));
            constructorBuilder.append(constructor.getName()).append('(');

            Parameter[] parameters = constructor.getParameters();
            if (parameters.length > 0) {
                for (int index = 0; index < parameters.length - 1; ++index) {
                    constructorBuilder.append(parameters[index].getType().getSimpleName()).append(", ");
                }
                constructorBuilder.append(parameters[parameters.length - 1].getType().getSimpleName());
            }
            constructorBuilder.append(");\n");
            classSignature.append(constructorBuilder);
        }
        return classSignature.toString();
    }

    private String getMethods(Class<?> cls) {
        StringBuilder classSignature = new StringBuilder();
        for (Method method : cls.getDeclaredMethods()) {
            StringBuilder methodBuilder = new StringBuilder();
            int methodModifiers = method.getModifiers();
            methodBuilder.append('\t');
            methodBuilder.append(getAccessModifierName(methodModifiers));
            methodBuilder.append(method.getReturnType().getSimpleName()).append(' ');
            methodBuilder.append(method.getName()).append('(');

            Parameter[] parameters = method.getParameters();
            if (parameters.length > 0) {
                for (int index = 0; index < parameters.length - 1; ++index) {
                    methodBuilder.append(parameters[index].getType().getSimpleName()).append(' ').append(", ");
                }
                methodBuilder.append(parameters[parameters.length - 1].getType().getSimpleName());
            }
            methodBuilder.append(");\n");
            classSignature.append(methodBuilder);
        }
        return classSignature.toString();
    }

    public void printClassInfo(Class<?> cls) {
        StringBuilder classSignature = new StringBuilder();
        int modifiers = cls.getModifiers();
        classSignature.append(getAccessModifierName(modifiers));
        classSignature.append(getClassType(modifiers));
        classSignature.append(cls.getCanonicalName());
        classSignature.append(" {\n");
        classSignature.append(getFields(cls));
        classSignature.append(getConstructors(cls));
        classSignature.append(getMethods(cls));
        classSignature.append("}");
        System.out.println(classSignature);
        System.out.println();
    }

    public void invokeTestMethodsOfClass(Class<?> cls) {
        if (!cls.isAnnotationPresent(MockAnnotation.class) || !Modifier.isPublic(cls.getModifiers())) {
            return;
        }
        System.out.println("---Class " + cls.getSimpleName() + " test method calls---");
        int testSuccess = 0, testTotal = 0;

        for (Method method : cls.getMethods()) {
            if (!method.isAnnotationPresent(Test.class)) {
                continue;
            }

            Object[] args = new Object[method.getParameters().length];
            Random random = new Random();
            Faker faker = new Faker();
            int index = 0;
            for (Parameter param : method.getParameters()) {
                if (param.getType() == int.class || param.getType() == Integer.class ||
                        param.getType() == byte.class || param.getType() == Byte.class ||
                        param.getType() == short.class || param.getType() == Short.class) {
                    int currParam = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                    args[index] = currParam;
                }
                else if (param.getType() == long.class || param.getType() == Long.class) {
                    long currParam = random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
                    args[index] = currParam;
                }
                else if (param.getType() == float.class || param.getType() == Float.class) {
                    float currParam = random.nextFloat(Float.MIN_VALUE, Float.MAX_VALUE);
                    args[index] = currParam;
                }
                else if (param.getType() == double.class || param.getType() == Double.class) {
                    double currParam = random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
                    args[index] = currParam;
                }
                else if (param.getType() == boolean.class || param.getType() == Boolean.class) {
                    boolean currParam = random.nextBoolean();
                    args[index] = currParam;
                }
                else if (param.getType() == char.class || param.getType() == Character.class) {
                    char currParam = (char) random.nextInt();
                    args[index] = currParam;
                }
                else if (param.getType() == String.class) {
                    String currParam = faker.name().name();
                    args[index] = currParam;
                }
                index++;
            }

            Object obj = null;
            method.setAccessible(true);
            try {
                obj = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (obj == null) {
                continue;
            }

            try {
                Object retObj = method.invoke(obj, args);
                testSuccess++;
                if (retObj != null) {
                    System.out.println("Method " + method.getName() + " returned: " + retObj.toString());
                }
            } catch (InvocationTargetException e) {

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                testTotal++;
            }
        }
        System.out.println("---Tests passed: " + testSuccess + "/" + testTotal + "---");
        System.out.println();
    }

    public static void compileFile(String path) {
        File[] files = new File[1];
        files[0] = new File(path);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager =
                compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));
        OutputStream stream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(System.out);

        compiler.getTask(writer, fileManager, null, null, null, compilationUnits).call();

        try {
            writer.close();
            stream.close();
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
