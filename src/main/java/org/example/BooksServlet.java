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

@WebServlet(name = "BooksServlet", urlPatterns = "/api/books")
public class BooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        Book book = getBookById(bookId);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(book));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equalsIgnoreCase(action)) {
            insertBook(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateBook(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteBook(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action specified.");
        }
    }

    private Book getBookById(int id) {
        Book book = null;
        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM books WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setPublicationYear(rs.getInt("publication_year"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setPrice(rs.getInt("price"));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        int publicationYear = Integer.parseInt(request.getParameter("publication_year"));
        int authorId = Integer.parseInt(request.getParameter("author_id"));
        int price = Integer.parseInt(request.getParameter("price"));

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO books (title, publication_year, author_id, price) VALUES (?, ?, ?, ?)");

            st.setString(1, title);
            st.setInt(2, publicationYear);
            st.setInt(3, authorId);
            st.setInt(4, price);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Book inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to insert book.");
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int publicationYear = Integer.parseInt(request.getParameter("publication_year"));
        int authorId = Integer.parseInt(request.getParameter("author_id"));
        int price = Integer.parseInt(request.getParameter("price"));

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE books SET title = ?, publication_year = ?, author_id = ?, price = ? WHERE id = ?");

            st.setString(1, title);
            st.setInt(2, publicationYear);
            st.setInt(3, authorId);
            st.setInt(4, price);
            st.setInt(5, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Book updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to update book.");
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("DELETE FROM books WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Book deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete book.");
        }
    }
}
