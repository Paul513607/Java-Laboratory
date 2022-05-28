import org.junit.jupiter.api.Test;
import util.Timer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void performanceTest() {
        Timer timer = Timer.getInstance();
        timer.start();
        try {
            Runtime.getRuntime().exec("java classpath:Main");
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.stop();
        assertTrue(timer.getDuration() < 10.0);
    }
}