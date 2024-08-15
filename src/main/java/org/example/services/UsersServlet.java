package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.*;
import org.example.utils.HibernateUtil;
import org.example.utils.UserDAO;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = "/api/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDAO userDAO = new UserDAO();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            userDAO.em = session;

            List<User> users = userDAO.findAllUsers();

            if (users == null || users.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("No users found");
                return;
            }

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(users);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().write(json);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error occurred while processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userType = req.getParameter("userType");

            User user;
            switch (userType) {
                case "Admin" -> user = new Admin();
                case "Customer" -> user = new Customer();
                case "Moderator" -> user = new Moderator();
                default -> {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid user type");
                    return;
                }
            }

            user.setId(0);
            user.setUsername(req.getParameter("username"));
            user.setPassword(req.getParameter("password"));

            UserDAO userDAO = new UserDAO();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            userDAO.em = session;

            userDAO.saveUser(user);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("User created successfully");

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error occurred while processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = req.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            String requestBody = sb.toString();

            String[] params = requestBody.split("&");
            int id = -1;
            String username = null;
            String password = null;
            String userType = null;
            for (String param : params) {
                String[] keyValue = param.split("=");
                switch (keyValue[0]) {
                    case "id" -> id = Integer.parseInt(keyValue[1]);
                    case "username" -> username = keyValue[1];
                    case "password" -> password = keyValue[1];
                    case "userType" -> userType = keyValue[1];
                }
            }

            if (id == -1 || username == null || password == null || userType == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Id, username, password, and user type are required");
                return;
            }

            UserDAO userDAO = new UserDAO();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            userDAO.em = session;

            User user = userDAO.findUserById(id);
            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("User not found");
                return;
            }

            user.setUsername(username);
            user.setPassword(password);

            userDAO.updateUser(user);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("User updated successfully");

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error occurred while processing request: " + e.getMessage());
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = req.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            String requestBody = sb.toString();

            String[] params = requestBody.split("&");
            int id = -1;
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue[0].equals("id")) {
                    id = Integer.parseInt(keyValue[1]);
                    break;
                }
            }

            if (id == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Id is required");
                return;
            }

            UserDAO userDAO = new UserDAO();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            userDAO.em = session;

            userDAO.deleteUser(id);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("User deleted successfully");

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error occurred while processing request: " + e.getMessage());
        }
    }
}