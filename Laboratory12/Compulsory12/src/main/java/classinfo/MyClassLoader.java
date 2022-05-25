package classinfo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

@Data
@NoArgsConstructor
public class MyClassLoader {
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    public void loadClass(String name) {
        Class cls = null;
        try {
            cls = classLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (cls != null) {
            System.out.println("Package name for class "  + cls.getName() + " is: " + cls.getPackageName());
            printInfo(cls);
            callStaticTestMethodsWithNoParameters(cls);
        }
    }

    public void printInfo(Class cls) {
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

        System.out.print("Fields: ");
        Arrays.stream(cls.getFields()).forEach(field -> System.out.print(field.getName() + " "));
        System.out.println();
    }

    public void callStaticTestMethodsWithNoParameters(Class cls) {
        Arrays.stream(cls.getMethods()).forEach(method -> {
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            boolean hasNoParams = (method.getParameters().length == 0);
            boolean isTestAnnotated = Arrays.stream(method.getAnnotations())
                    .filter(annotation -> annotation.annotationType() == Test.class)
                    .findFirst()
                    .orElse(null) != null;

            if (isStatic && hasNoParams && isTestAnnotated) {
                try {
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
