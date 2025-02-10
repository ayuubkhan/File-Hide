package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

//Inserting the value or not
public class UserService {
    public static Integer saveUser(User user) {
        try {
            if (UserDAO.isExists(user.getEmail())) {
                return 0;
            } else {
                return UserDAO.saveUser(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
