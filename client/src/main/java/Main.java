import ui.Repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080";
        Repl menu = new Repl(serverUrl);
        System.out.println("♕ CS240 Chess Client");
        System.out.println("Type 'Help' to get started");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("quit")) {
            System.out.println("You entered quit.");
        }
        else{
            menu.handleInput(input);
        }
    }
}
