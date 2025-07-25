package services;

import database.AdminDAO;
import models.Admin;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private AdminDAO adminDAO = new AdminDAO();

    public boolean authenticate(String username, String password) {
        try {
            Admin admin = adminDAO.getByUsername(username);
            if (admin != null && BCrypt.checkpw(password, admin.passwordHash)) {
                return true;
            }
        } catch (Exception e) {
            // log error
        }
        return false;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
