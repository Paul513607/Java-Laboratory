import java.util.ArrayList;

public class Compulsory1 {
    static int getResult() {
        int n = (int) (Math.random() * 1_000_000);
        System.out.println(n);
        n = n * 3;
        n = n + Integer.parseInt("10101", 2);
        n = n + Integer.parseInt("FF", 16);
        n = n * 6;
        System.out.println(n);
        return n;
    }

    static int sumDigits(int n) {
        int sum = 0;
        while (n > 0) {
            sum = sum + n % 10;
            n /= 10;
        }
        return sum;
    }

    static int controlNumber(int n) {
        while (n > 9) {
            n = Compulsory1.sumDigits(n);
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String[] programmingLanguages = new String[] {
                "C",
                "C++",
                "C#",
                "Python",
                "Go",
                "Rust",
                "JavaScript",
                "PHP",
                "Swift",
                "Java"
        };

        int result = Compulsory1.getResult();
        result = Compulsory1.controlNumber(result);
        System.out.println(result);
        System.out.println("Willy-nilly, this semester I will learn " + programmingLanguages[result] + ".");
    }
}
