package ui;

import model.AuthData;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;
import static ui.EscapeSequences.RESET_TEXT_UNDERLINE;
import static ui.EscapeSequences.SET_TEXT_UNDERLINE;

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
    private UserState currentState = UserState.LOGGED_OUT;
    ServerFacade server = new ServerFacade();
    private GameData[] gameList;private
    Map<Integer, Integer> gameIdMap = new HashMap<>();
    Map<Integer, Integer> reverseMap = new HashMap<>();



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
        System.out.println("if LOGGED_OUT: options = login" + "\n" + "register" + "\n" + "help" + "\n" + "quit");
        System.out.println("if LOGGED_IN: options = logout" + "\n" + "list" + "\n" + "createGame" + "\n" + "join"
                + "\n" + "observe" + "\n" + "help" + "\n" + "quit");
    }

    private void login(String[] params) throws Exception{
        username = params[1];
        password = params[2];
        server.login(username,password);
    }

    private void register(String[] params) throws Exception{
        username = params[1];
        password = params[2];
        String email = params[3];
        server.register(username,password,email);
    }

    private void logout()throws Exception{
        server.logout(authToken);
        currentState = UserState.LOGGED_OUT;
        username = null;
        authToken = null;
    }

    private void quit() throws Exception{
        if (currentState.equals(UserState.LOGGED_IN)) {
            logout();
        }
        exit(0);
    }

    private void list(String[] params) throws Exception{
        updateGameList();

        for (GameData gameData : gameList) {

            int gameId = gameIdMap.get(gameData.getGameID());

            System.out.print("Game Name: " + gameData.getGameName());
            System.out.print(" Game ID: "+ gameId);
            System.out.print(" White Player: " + gameData.getWhiteUsername() != null ? gameData.getWhiteUsername() : "Empty");
            System.out.print(" Black Player: " + gameData.getBlackUsername() != null ? gameData.getBlackUsername() : "Empty" + "\n");
        }
    }
    private void createGame(String[] params) throws Exception{

    }



    private void updateGameList() throws Exception {
        gameList = server.listGames(authToken);
        for (int i = 0; i < gameList.length; i++) {
            gameIdMap.put(gameList[i].getGameID(), (i + 1));
            reverseMap.put((i + 1), gameList[i].getGameID());
        }
    }


}