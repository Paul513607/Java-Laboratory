package server;

import lombok.Data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/** The server class. */
@Data
public class Server {
    private final int PORT = 5000;
    private ServerSocket serverSocket = null;
    public boolean isStopped = false;
    public int connectionCount = 1;

    public Server() {
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = null;
            if (!isStopped) {
                try {
                    System.out.println("Waiting for a client...");
                    socket = serverSocket.accept();
                    connectionCount++;
                } catch (SocketException e) {
                    System.out.println("Stopped accepting");
                }
            }

            if (connectionCount == 1) {
                System.out.println("Server stopped.");
                break;
            }

            if (!isStopped) {
                (new ClientThread(this, socket)).start();
            }
        }
    }
}
