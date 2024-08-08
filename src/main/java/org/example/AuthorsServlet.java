package org.example;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "AuthorsServlet", urlPatterns = "/api/authors")
public class AuthorsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int authorId = Integer.parseInt(request.getParameter("id"));
        Author author = getAuthorById(authorId);

        if (author == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Author not found for id: " + authorId);
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(author));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equalsIgnoreCase(action)) {
            insertAuthor(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateAuthor(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteAuthor(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action specified.");
        }
    }

    private Author getAuthorById(int id) {
        Author author = null;
        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM authors WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }

    private void insertAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO authors (name) VALUES (?)");

            st.setString(1, name);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Author inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to insert author.");
        }
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE authors SET name = ? WHERE id = ?");

            st.setString(1, name);
            st.setInt(2, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Author updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to update author.");
        }
    }

    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("DELETE FROM authors WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Author deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete author.");
        }
    }
}
