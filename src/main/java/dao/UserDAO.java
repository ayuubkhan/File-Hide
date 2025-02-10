package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Data Access Object
public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES(default, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        return  ps.executeUpdate();
    }

    public static String getUserName(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT name FROM users WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("name");
        }
        return null; // Agar nahi mila toh null return karo
    }

    public static int deleteUser(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement psData = connection.prepareStatement("DELETE FROM data WHERE email = ?");
        psData.setString(1,email);
        psData.executeUpdate();


        PreparedStatement psUser = connection.prepareStatement("DELETE FROM users WHERE email = ?");
        psUser.setString(1,email);
        int result = psUser.executeUpdate();

        return result;
    }



}
