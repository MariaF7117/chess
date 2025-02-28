package service;

import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import model.GameData;

public class GameService {
    private GameDAO gameDAO;

    public GameData createGame(GameData gameData) throws DataAccessException {
        if (gameData.getGameName() == null || gameData.getGameName().isEmpty()) {
            throw new DataAccessException("Invalid game name");
        }
        gameDAO.createGame(gameData);
        return gameDAO.getGame(gameData.getGameId());
    }

    public GameData getGame(int gameId) throws DataAccessException {
        return gameDAO.getGame(gameId);
    }

    public void deleteGame(int gameId) throws DataAccessException {
        gameDAO.deleteGame(gameId);
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }
}