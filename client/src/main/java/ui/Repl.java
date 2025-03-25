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
    private UserState currentState = UserState.LOGGED_OUT;
    String serverUrl = "http://localhost:8080";
    ServerFacade server = new ServerFacade(serverUrl);
    private GameData[] gameList;
    Map<Integer, Integer> gameIdMap = new HashMap<>();
    Map<Integer, Integer> reverseMap = new HashMap<>();
    private final DrawBoard drawBoard = new DrawBoard();

    public Repl(String serverUrl){
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
            System.out.println("Login failed: That username and password combination does not exist.");
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
            if (params.length < 4) {
                throw new IllegalArgumentException("Registration requires a username, password, and email.");
            }

            username = params[1];
            password = params[2];
            String email = params[3];

            server.register(username, password, email);
            loginUser(username, password);
            System.out.println("Registration successful! You are now logged in.");
        }catch (IllegalArgumentException e){
            System.out.println("Registration failed: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Registration failed: user exists: login or register different user");
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

        if(gameList.length == 0){
            System.out.println("There are no games in list create game");
        }
        else{
            for (int i = 0; i < gameList.length; i++) {
                GameData gameData = gameList[i];
                int gameId = i + 1;

                System.out.println("Game " + gameId + ": " + gameData.getGameName());
                if (gameData.getWhiteUsername() == null) {
                    System.out.println("  White Player: Available");
                } else {
                    System.out.println("  White Player: " + gameData.getWhiteUsername());
                }
                if (gameData.getBlackUsername() == null) {
                    System.out.println("  Black Player: Available");
                } else {
                    System.out.println("  Black Player: " + gameData.getBlackUsername());
                }
                System.out.println();
            }
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
            String gameName = nameBuilder.toString().trim();

            gameData = server.createGame(gameName, authToken);
            updateGameList();

            int gameId = gameIdMap.get(gameData.getGameID());
            System.out.println("Game '" + gameName + "' created with ID: " + gameId);
        } catch (Exception e) {
            System.out.println("Failed to create game: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"));
        }
    }

    private void join(String[] params) throws Exception{
        try {
            if (params.length < 3) {
                System.out.println("Error: You must provide a game ID and a color. See 'help' for usage.");
                return;
            }

            String teamColor = params[2].toUpperCase();
            updateGameList();
            int joinGameID = Integer.parseInt(params[1]);

            if (!reverseMap.containsKey(joinGameID)) {
                System.out.println("Error: Invalid game ID. Use 'list' to see available games.");
                return;
            }

            joinGameID = reverseMap.get(joinGameID);
            ChessGame.TeamColor team = ChessGame.TeamColor.valueOf(teamColor);

            GameData gameData = server.joinGame(joinGameID, team, authToken);


            if(team == ChessGame.TeamColor.WHITE && gameData.getWhiteUsername() == null || team == ChessGame.TeamColor.BLACK && gameData.getBlackUsername() == null){
            if (team == ChessGame.TeamColor.BLACK) {
                currentState = UserState.BLACK;
            }
            else {
                currentState = UserState.WHITE;
            }}

            drawBoard.printBothBoards();
            updateGameList();

            System.out.println("Successfully joined game as " + team + ".");

        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid team color. Choose 'WHITE' or 'BLACK'.");
        } catch (Exception e) {
            System.out.println("Failed to join game: this player is already taken or unavailable right now");
        }

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
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid game ID format. It should be a number.");
        } catch (Exception e) {
            System.out.println("Failed to observe game: " + e.getMessage());
        }
    }


    private void updateGameList() throws Exception {
        gameList = server.listGames(authToken);
        gameIdMap.clear();
        reverseMap.clear();
        for (int i = 0; i < gameList.length; i++) {
            gameIdMap.put(gameList[i].getGameID(), (i + 1));
            reverseMap.put((i + 1), gameList[i].getGameID());
        }
    }

}