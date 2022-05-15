package server;

import commands.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

/** Class which handles a client. */
@Data
@NoArgsConstructor
public class ClientThread extends Thread {
    private Server server;
    private Socket socket;
    private User user;
    private boolean isLoggedIn = false;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private boolean checkIfLoggedIn(List<String> args) {
        return isLoggedIn || args.get(0).equals("login")
                || args.get(0).equals("register") || args.get(0).equals("help");
    }

    public Command getCommand(List<String> args) {
        switch (args.get(0)) {
            case "help":
                return new HelpCommand(args);
            case "register":
                return new RegisterCommand(args);
            case "login":
                return new LoginCommand(args);
            case "friend":
                return new FriendCommand(args, user);
            case "send":
                return new SendCommand(args, user);
            case "read":
                return new ReadCommand(args, user);
            default:
                return null;
        }
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            socket.setSoTimeout(1000000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            boolean isOn = true;
            while (isOn) {
                String request = in.readLine();
                List<String> args = CommandParser.parseCommand(request);

                String answer;
                if (!checkIfLoggedIn(args)) {
                    answer = "You must be logged in to enter commands. Specify 'help' for command uses.";
                    out.println(answer);
                    out.flush();
                    continue;
                }

                if (request.equals("stop")) {
                    System.out.println("Server stopped!");
                    answer = "Server stopped!";
                    isOn = false;
                    server.isStopped = true;
                    server.connectionCount--;
                    out.println(answer);
                    out.flush();
                    try {
                        this.server.getServerSocket().close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    continue;
                }

                Command cmd;
                cmd = getCommand(args);

                if (cmd == null) {
                    answer = "Unknown command";
                    out.println(answer);
                    out.flush();
                    continue;
                }

                cmd.execute();
                System.out.println(cmd.getClass().getName());
                if (cmd.isStatusCode()) {
                    answer = cmd.getOutput();
                    if (args.get(0).equals("login")) {
                        this.user = ((LoginCommand) cmd).getCurrentUser();
                        this.isLoggedIn = true;
                    }
                } else {
                    answer = cmd.getErrorMsg();
                }

                out.println(answer);
                out.flush();
            }
        } catch (SocketTimeoutException e) {
            assert out != null;
            System.out.println("Server timeout");
            out.println("Server timeout");
            out.flush();
            server.isStopped = true;
            server.connectionCount--;
            try {
                this.server.getServerSocket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
