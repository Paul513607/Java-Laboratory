import client.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public void startClient() {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Input a request: ");
            String request = scanner.nextLine();
            String[] argsArr = request.split(" ");
            List<String> args = Arrays.stream(argsArr).toList();

            try {
                switch (args.get(0)) {
                    case "register" -> client.register(args.get(1));
                    case "login" -> client.login(args.get(1));
                    case "getUsers" -> client.getUsers();
                    case "updateUser" -> client.updateUser(Long.parseLong(args.get(1)),
                            args.get(2));
                    case "deleteUser" -> client.deleteUser(Long.parseLong(args.get(1)),
                            args.get(2));
                    case "createFriendship" -> client.createFriendship(client.getName(),
                            args.get(1));
                    case "deleteFriendship" -> client.deleteFriendship(client.getName(),
                            args.get(1));
                    case "getFriendsOf" -> client.getFriendsOf(client.getName());
                    case "getMostPopularUsers" -> client.getMostPopularUsers(Integer.parseInt(args.get(1)));
                    case "getApiDocs" -> client.getApiDocs();
                    case "getFriendsCutpoints" -> client.getFriendsCutpoints();
                    case "hello" -> client.getHello();
                    case "exit" -> {
                        System.out.println("Exiting...");
                        isRunning = false;
                    }
                    default -> System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong with the request.");
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startClient();
    }
}
