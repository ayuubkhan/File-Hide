package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection con = null;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/FileHider?allowPublicKeyRetrieval=true&useSSL=false";
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("Connection closed successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
