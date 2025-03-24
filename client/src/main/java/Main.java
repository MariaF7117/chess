import ui.Repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080";
        Repl menu = new Repl(serverUrl);
        System.out.println("♕ CS240 Chess Client");
        System.out.println("Type 'Help' to get started");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Exiting Chess Client...");
                break;
            }
            menu.handleInput(input);
        }
        scanner.close();

    }
}
