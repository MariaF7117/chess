package model;

import chess.ChessGame;

import java.util.Objects;

public class GameData {

    private final int gameID;
    private  String whiteUsername;
    private  String blackUsername;
    private final String gameName;
    private final ChessGame game;

   public GameData(int gameId, String whiteUsername, String blackUsername, String gameName) {
        this.gameID = gameId;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new ChessGame();
    }

    public int getGameID() {
        return gameID;
    }
    public String getWhiteUsername() {
        return whiteUsername;
    }
    public String getBlackUsername() {
        return blackUsername;
    }
    public void setWhiteUsername(String whiteUsername) {
       this.whiteUsername =  whiteUsername;
    }
    public void setBlackUsername(String blackUsername) {
       this.blackUsername = blackUsername;
    }
    public String getGameName() {
        return gameName;
    }
    public ChessGame getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameData gameData = (GameData) o;
        return gameID == gameData.gameID && Objects.equals(whiteUsername, gameData.whiteUsername) && Objects.equals(blackUsername, gameData.blackUsername) && Objects.equals(gameName, gameData.gameName) && Objects.equals(game, gameData.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public String toString() {
        return "GameData{" +
                "gameId=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", gameName='" + gameName + '\'' +
                ", game=" + game +
                '}';
    }
}
