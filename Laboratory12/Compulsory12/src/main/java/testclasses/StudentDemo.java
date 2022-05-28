package testclasses;

import customannotations.MockAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MockAnnotation
public class StudentDemo {
    private String name;
    private Integer age;

    @Test
    public static void hello() {
        System.out.println("Hello world!");
        Assertions.assertTrue(true);
    }

    @Test
    public int get0() {
        System.out.println("You got 0.");
        Assertions.assertTrue(false);
        return 0;
    }

    public void sayHi() {
        System.out.println("Hi");
    }

    @Test
    public static void helloWithParam(String param) {
        System.out.println("Hello " + param + "!");
        Assertions.assertTrue(true);
    }

    @Test
    public static int getMeaningOfLife() {
        System.out.println("You've found the meaning of life.");
        Assertions.assertTrue(true);
        return 42;
    }

    public static void wrongMethod() {
        System.out.println("Wrong method!");
    }
}
