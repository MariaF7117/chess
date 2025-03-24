import ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080";
        Menu menu = new Menu(serverUrl);

        System.out.println("♕ CS240 Chess Client");
        System.out.println("Type 'Help' to get started");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    break;
                }

                if (!input.isEmpty()) {
                    menu.handleInput(input);
                }
            }
        }
    }
}