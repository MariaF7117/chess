package ui;

import model.UserData;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_MAGENTA;

public class Menu {

    private final ServerFacade serverFacade;
    private boolean isLoggedIn;
    private String authToken;

    private String username;

    public Menu(String serverUrl){
        this.serverFacade = new ServerFacade(serverUrl);
        this.isLoggedIn = false;
        this.authToken = null;
    }

    public void handleInput(String input) throws Exception{
        String[] tokens = input.split("\\s+", 2);
        input = tokens[0].toLowerCase();

        if(!isLoggedIn){
            handlePreLoggedIn(input, tokens);
        }
        else if(isLoggedIn){
            handlePostLoggedIn(input,tokens);
        }
    }

    public void handlePreLoggedIn(String input, String[] tokens) throws Exception{
        switch (input) {
            case "help":
                printPreloginHelp();
                break;
            case "login":
                login(tokens);
                break;
            case "register":
                register(tokens);
                break;
        }
    }
    public void handlePostLoggedIn(String input, String[] tokens) throws Exception{
        switch (input) {
            case "help":
                printPostloginHelp();
                break;
            case "logout":
                logout();
                break;
            case "create":
                createGame(tokens);
                break;
            case "list":
                listGames();
                break;
            case "play":
                joinGame(tokens);
                break;
        }
    }

    public void printPreloginHelp()throws Exception{
        StringBuilder returnString = new StringBuilder();

        returnString.append(SET_TEXT_COLOR_MAGENTA + "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR)
                .append( " - Login to an existing account." + "\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "register <USERNAME> <PASSWORD> <EMAIL>" + RESET_TEXT_COLOR)
                .append( " - Register a new account." + "\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "help" + RESET_TEXT_COLOR + " - List possible operations." + "\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "quit" + RESET_TEXT_COLOR + " - Quit the program." + "\n");
        System.out.print(returnString);
    }
    public void printPostloginHelp() throws Exception{
        StringBuilder returnString = new StringBuilder();

        returnString.append(SET_TEXT_COLOR_MAGENTA).append("logout").append(RESET_TEXT_COLOR)
                .append(" - Log out of ").append(username).append(".").append("\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA).append("create <Name>").append(RESET_TEXT_COLOR).append(" - Creates a new game.\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA).append("list").append(RESET_TEXT_COLOR).append(" - Lists all active games.\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA).append("join <ID> [WHITE|BLACK]").append(RESET_TEXT_COLOR)
                .append(" - Joins a game with the given ID.\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "help" + RESET_TEXT_COLOR + " - List possible operations." + "\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "quit" + RESET_TEXT_COLOR + " - Quit the program." + "\n");
        System.out.print(returnString);

    }
    public void login(String[] tokens) throws Exception{


    }
    public void register(String[] tokens)throws Exception{

    }
    public void logout()throws Exception{

    }
    public void createGame(String[] tokens)throws Exception{

    }
    public void listGames()throws Exception{

    }
    public void joinGame(String[] tokens)throws Exception{

    }
}