package tests;

import database.UserDAO;
import models.User;

public class UserDAOTest {
    public static void main(String[] args) throws Exception {
        UserDAO dao = new UserDAO();
        User u = new User();
        u.cardNumber = "123456";
        u.firstName = "Test";
        u.lastName = "User";
        u.email = "test@example.com";
        u.phone = "1234567890";
        u.department = "IT";
        u.role = "user";
        u.enabled = true;
        dao.add(u);

        User fetched = dao.getByCardNumber("123456");
        assert fetched != null && fetched.firstName.equals("Test");
        System.out.println("UserDAO test passed.");
    }
}
