package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {
    @Override
    public UserData createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO users VALUES (?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
             String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
             ps.setString(1, user.getUsername());
             ps.setString(2, hashedPassword);
             ps.setString(3, user.getEmail());
             ps.executeUpdate();
             return user;
        }catch (SQLException e){
            throw new DataAccessException("SQL Create Error");
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
        }
        else
            return null;

        } catch (SQLException e) {
            throw new DataAccessException("SQL getUser Error");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM users";
        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Clear Error");
        }
    }
}
