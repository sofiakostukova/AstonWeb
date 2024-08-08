package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AllBooks", urlPatterns = "/api/AllBooks")
public class AllBooks extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = getAllBooks();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().create();
        out.print(gson.toJson(books));
        out.flush();
    }

    private List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            Class.forName(DbUtil.driver);
            conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            st = conn.prepareStatement("SELECT * FROM books");
            rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setPublicationYear(rs.getInt("publication_year"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setPrice(rs.getInt("price"));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return books;
    }
}
