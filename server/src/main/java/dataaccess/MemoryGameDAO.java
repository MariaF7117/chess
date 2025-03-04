package dataaccess;

import model.GameData;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryGameDAO implements GameDAO {

    private final Map<Integer, GameData> games = new HashMap<>();

    public MemoryGameDAO() {}

    @Override
    public GameData createGame(GameData game) throws DataAccessException {

        int newGameId = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
        GameData newGame = new GameData(newGameId, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName());
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
    public void updateGame(GameData game) throws DataAccessException {
        if (!games.containsKey(game.getGameID())) {
            throw new DataAccessException("Game not found: Cannot update");
        }
        games.put(game.getGameID(), game);
    }

    @Override
    public void deleteGame(int gameID) throws DataAccessException {
        games.remove(gameID);
    }

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

}
