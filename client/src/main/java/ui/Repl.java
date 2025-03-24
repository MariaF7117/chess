package ui;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.HashMap;
import java.util.Map;
public class Repl {
    private enum UserState {
        LOGGED_OUT,
        LOGGED_IN,
        WHITE,
        BLACK,
        OBSERVER
    }

    private String username;
    private String password;
    private String authToken;
    private GameData gameData;
    public String serverUrl;
    private UserState currentState = UserState.LOGGED_OUT;
    ServerFacade server = new ServerFacade();
    private GameData[] gameList;private
    Map<Integer, Integer> gameIdMap = new HashMap<>();
    Map<Integer, Integer> reverseMap = new HashMap<>();
    private final DrawBoard drawBoard = new DrawBoard();




    public Repl(String serverUrl){
        serverUrl = "http://localhost:8080";
    }

    public void handleInput(String input)throws Exception{
        String[] params = input.split(" ");
        String command = params[0].toLowerCase();

        switch (currentState){
            case LOGGED_OUT:
                switch (command){
                    case "login" -> login(params);
                    case "register" -> register(params);
                    case "help" -> printHelp();
                    case "quit" -> quit();
                    default -> System.out.println("Invalid command: " + command);
                }
                break;
            case LOGGED_IN, WHITE, BLACK, OBSERVER:
                switch (command) {
                    case "logout" -> logout();
                    case "list" -> list();
                    case "create" -> createGame(params);
                    case "join" -> join(params);
                    case "observe" -> observe(params);
                    case "help" -> printHelp();
                    case "quit" -> quit();
                    default -> System.out.println("Invalid command: " + command);
                }
                break;
        }
    }

    private void printHelp(){
        if(currentState == UserState.LOGGED_OUT){
            System.out.println("register <username> <password> <email>");
            System.out.println("login <username> <password>");
            System.out.println("help");
            System.out.println("quit");

        }
        else if(currentState == UserState.LOGGED_IN) {
            System.out.println("logout <username> <password> <email>");
            System.out.println("list");
            System.out.println("create <gameName>");
            System.out.println("join <gameID> <color>");
            System.out.println("observe <gameID>");
            System.out.println("help");
            System.out.println("quit");
        }
    }

    private void login(String[] params) throws Exception{
        try {
            if (params.length < 3) {
                throw new IllegalArgumentException("Login requires a username and password.");
            }
            username = params[1];
            password = params[2];
            loginUser(username, password);
            System.out.println("Successfully Logged In, type 'help' for options");
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        } catch (Exception e) {
            if (e.getMessage().contains("User does not exist")) {
                System.out.println("Login failed: Username not found. Please register first.");
            } else if (e.getMessage().contains("Invalid password")) {
                System.out.println("Login failed: Incorrect password. Please try again.");
            } else {
                System.out.println("Login failed: " + e.getMessage());
            }
        }
    }
    private void loginUser(String username, String password) throws Exception {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
        AuthData loginData = server.login(username, password);
        authToken = loginData.authToken();
        currentState = UserState.LOGGED_IN;
    }

    private void register(String[] params) throws Exception{
        try {
            username = params[1];
            password = params[2];
            String email = params[3];

            server.register(username, password, email);
            loginUser(username, password);
            System.out.println("Registration successful! You are now logged in.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: Invalid input. Please check your details and try again.");
        } catch (Exception e) {
            if (e.getMessage().contains("User already exists")) {
                System.out.println("Registration failed: Username already taken. Try a different one.");
            } else if (e.getMessage().contains("already logged in")) {
                System.out.println("Registration failed: You are already logged in. Logout before registering a new account.");
            } else {
                System.out.println("Registration failed: " + e.getMessage());
            }
        }
    }

    private void logout()throws Exception{
        server.logout(authToken);
        currentState = UserState.LOGGED_OUT;
        username = null;
        authToken = null;
        System.out.println("Successfully logged out, type 'help' for options");
    }

    private void quit() throws Exception{
        if (currentState.equals(UserState.LOGGED_IN)) {
            logout();
        }
    }

    private void list() throws Exception{
        updateGameList();

        for (GameData gameData : gameList) {

            int gameId = gameIdMap.get(gameData.getGameID());

            System.out.println("Game Name: " + gameData.getGameName());
            System.out.println(" Game ID: "+ gameId);
            System.out.println(" White Player: " + gameData.getWhiteUsername() != null ? gameData.getWhiteUsername() : "Empty");
            System.out.println(" Black Player: " + gameData.getBlackUsername() != null ? gameData.getBlackUsername() : "Empty" + "\n");
        }
    }
    private void createGame(String[] params) {
        try {
            if (params.length < 2) {
                System.out.println("Error: You must provide a game name. See 'help' for usage.");
                return;
            }

            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                nameBuilder.append(params[i]).append(" ");
            }
            String gameName = nameBuilder.toString().trim(); // Trim trailing space

            gameData = server.createGame(gameName, authToken);
            updateGameList();

            int gameId = gameIdMap.get(gameData.getGameID());
            System.out.println("Game '" + gameName + "' created with ID: " + gameId);
        } catch (Exception e) {
            System.out.println("Failed to create game: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"));
            e.printStackTrace(); // Debugging purposes
        }
    }


    private void join(String[] params) throws Exception{
        String teamColor = params[2];
        updateGameList();
        int joinGameID = Integer.parseInt(params[1]);
        ChessGame.TeamColor team = ChessGame.TeamColor.valueOf(teamColor.toUpperCase());

        joinGameID = reverseMap.get(joinGameID);
        if (team == ChessGame.TeamColor.BLACK) {
            currentState = UserState.BLACK;
        }
        else {
            currentState = UserState.WHITE;
        }
        gameData = server.joinGame(joinGameID, team, authToken);
        //draw boards
        drawBoard.printBothBoards();
        updateGameList();

    }

    private void observe(String[] params) throws Exception {
        try {
            if (params.length < 2) {
                System.out.println("Error: You must provide a game ID. See 'help' for usage.");
                return;
            }

            int userInputID = Integer.parseInt(params[1]);
            updateGameList();

            Integer gameID = reverseMap.get(userInputID);
            if (gameID == null) {
                System.out.println("Error: Invalid game ID. Use 'list' to see available games.");
                return;
            }

            for (GameData game : gameList) {
                if (game.getGameID() == gameID) {
                    gameData = game;
                    currentState = UserState.OBSERVER;
                    drawBoard.printBothBoards();
                    System.out.println("Now observing game: " + gameData.getGameName());
                    return;
                }
            }

            System.out.println("Error: Game not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid game ID format. It should be a number.");
        } catch (Exception e) {
            System.out.println("Failed to observe game: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void updateGameList() throws Exception {
        gameList = server.listGames(authToken);
        for (int i = 0; i < gameList.length; i++) {
            gameIdMap.put(gameList[i].getGameID(), (i + 1));
            reverseMap.put((i + 1), gameList[i].getGameID());
        }
    }


}