package dataaccess;

import model.GameData;
import java.util.List;
import java.util.Optional;

public interface GameDAO {
    void createGame(GameData game) throws DataAccessException;
    Optional<GameData> getGame(int gameID) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    void clear() throws DataAccessException;
}
