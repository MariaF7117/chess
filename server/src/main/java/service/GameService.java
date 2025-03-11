package service;

import dataaccess.*;
import handler.errors.BadRequestException;
import handler.errors.UserExistsException;
import model.GameData;
import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameService {

    private GameDAO gameDAO;
    public GameService() {
        try{
            gameDAO = new SQLGameDAO();
        }
        catch(Exception e){
            gameDAO = new MemoryGameDAO();
        }
    }

    public GameData createGame(GameData gameData) throws BadRequestException, DataAccessException {
        if (gameData.getGameName() == null || gameData.getGameName().isEmpty()) {
            throw new BadRequestException("Invalid game name");
        }

        GameData createdGame = gameDAO.createGame(gameData);

        if (createdGame == null) {
            throw new DataAccessException("failed to create new game");
        }
        return createdGame;
    }
    public GameData joinGame(int gameID, String joinedColor, String username) throws UserExistsException, BadRequestException, DataAccessException {
        if (joinedColor == null || username == null || username.isEmpty()) {
            throw new BadRequestException("Invalid input: color or username is missing.");
        }

        GameData gameToJoin = gameDAO.getGame(gameID);
        if (gameToJoin == null) {
            throw new BadRequestException("Game not found.");
        }

        if (joinedColor.equalsIgnoreCase("WHITE")) {
            if (gameToJoin.getWhiteUsername() == null || gameToJoin.getWhiteUsername().isEmpty()) {
                gameToJoin.setWhiteUsername(username);
            } else {
                throw new UserExistsException("White spot is already taken.");
            }
        } else if (joinedColor.equalsIgnoreCase("BLACK")) {
            if (gameToJoin.getBlackUsername() == null || gameToJoin.getBlackUsername().isEmpty()) {
                gameToJoin.setBlackUsername(username);
            } else {
                throw new UserExistsException("Black spot is already taken.");
            }
        } else {
            throw new BadRequestException("Invalid color choice. Choose 'WHITE' or 'BLACK'.");
        }

        gameDAO.updateGame(gameToJoin);
        GameData updatedGame = gameDAO.getGame(gameID);

        return updatedGame;
    }

    public Collection<GameData> getAllGames() throws DataAccessException {
        return gameDAO.listGames();
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }
}