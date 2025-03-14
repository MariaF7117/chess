package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryGameDAO implements GameDAO {

    private final Map<Integer, GameData> games = new HashMap<>();

    public MemoryGameDAO() {}

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        ChessGame newChessGame = new ChessGame();
        int newGameId = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
        GameData newGame = new GameData(newGameId, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(),newChessGame);
        games.put(newGameId, newGame);

        return newGame;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new DataAccessException("Game not found");
        }
        System.out.println("Fetching game ID: " + gameID + " | White: " + game.getWhiteUsername() +
                ", Black: " + game.getBlackUsername());
        return game;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        if (games == null) {
            return new ArrayList<>();
        }
        return games.values();
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        if (!games.containsKey(game.getGameID())) {
            throw new DataAccessException("Game not found: Cannot update");
        }
        games.put(game.getGameID(), game);
        return game;
    }



    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

}
