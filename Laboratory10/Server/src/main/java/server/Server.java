package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 5000;
    private ServerSocket serverSocket = null;

    public Server() {
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);

        while (true) {
            System.out.println("Waiting for a client...");
            Socket socket = serverSocket.accept();
            (new ClientThread(socket)).start();
        }
    }
}
