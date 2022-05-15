import server.Server;
import svgapp.TestSVGGen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {
    public void testSVG() {
        TestSVGGen testSVGGen = new TestSVGGen();
        testSVGGen.createSVG();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.testSVG();
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
