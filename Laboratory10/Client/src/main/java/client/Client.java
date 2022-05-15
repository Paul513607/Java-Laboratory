package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/** The basic TCP client class. */
@Data
@NoArgsConstructor
public class Client {
    private final String SERVER_ADDRESS = "127.0.0.1"; // localhost
    private final int PORT = 5000;

    public void makeRequest() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader (
                    new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            boolean isMakingRequests = true;
            while(isMakingRequests) {
                System.out.print("Enter a request for the server or 'exit' to stop: ");
                String request = scanner.nextLine();

                if (request.equals("exit")) {
                    out.println("stop");
                    isMakingRequests = false;
                } else {
                    out.println(request);
                }

                String response = in.readLine();
                response = response.replaceAll("\\|", "\n");
                System.out.println(response);
                if (response.equals("Server timeout")) {
                    isMakingRequests = false;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("No server listening... " + e);
        }
    }
}
