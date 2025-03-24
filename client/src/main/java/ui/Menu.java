package ui;

import model.UserData;

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

    public void handleInput(String input){
        String[] tokens = input.split("\\s+", 2);
        input = tokens[0].toLowerCase();

        if(!isLoggedIn){
            handlePreLoggedIn(input, tokens);
        }
        else if(isLoggedIn){
            handlePostLoggedIn(input,tokens);
        }
    }

    public void handlePreLoggedIn(String input, String[] tokens) {
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
    public void handlePostLoggedIn(String input, String[] tokens){
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

    public String printPreloginHelp(){
        return "";
    }
    public String printPostloginHelp(){
        return "";
    }
    public void login(String[] tokens){

    }
    public void register(String[] tokens){

    }
    public void logout(){

    }
    public void createGame(String[] tokens){

    }
    public void listGames(){

    }
    public void joinGame(String[] tokens){

    }
}