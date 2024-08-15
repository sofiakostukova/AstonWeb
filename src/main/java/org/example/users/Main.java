package org.example.users;

import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                System.out.println("Admin Role: " + admin.getAdminRole());
            } else if (user instanceof Moderator) {
                Moderator moderator = (Moderator) user;
                System.out.println("Moderator Role: " + moderator.getModeratorRole());
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.println("Customer Type: " + customer.getCustomerType());
            }
            System.out.println();
        }
    }
}