package testclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDemo {
    private String name;
    private Integer age;

    @Test
    public static void hello() {
        System.out.println("Hello world!");
    }

    @Test
    public int get0() {
        System.out.println("You got 0.");
        return 0;
    }

    public void sayHi() {
        System.out.println("Hi");
    }

    @Test
    public static void helloWithParam(String param) {
        System.out.println("Hello " + param + "!");
    }

    @Test
    public static int getMeaningOfLife() {
        System.out.println("You've found the meaning of life.");
        return 42;
    }

    public static void wrongMethod() {
        System.out.println("Wrong method!");
    }
}
