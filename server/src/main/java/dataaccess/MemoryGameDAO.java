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

        //again why the error on GameData???

        int newGameId = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
        GameData newGame = new GameData(newGameId, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName());
        games.put(newGameId, newGame);

        return newGame;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        games.put(game.getGameId(), game);
    }

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

}
