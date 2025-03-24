import chess.*;
import ui.Menu;

import java.util.Scanner;

import static java.awt.SystemColor.menu;

public class Main {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080";
        Menu menu = new Menu(serverUrl);

        System.out.println("♕ CS240 Chess Client");
        System.out.println("Type 'Help' to get started");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                menu.printInput();
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