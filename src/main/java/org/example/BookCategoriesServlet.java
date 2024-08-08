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

@WebServlet(name = "BookCategoriesServlet", urlPatterns = "/api/book_categories")
public class BookCategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String bookIdParam = request.getParameter("book_id");
            String categoryIdParam = request.getParameter("category_id");

            if (bookIdParam == null || categoryIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameters: book_id or category_id");
                return;
            }

            int bookId = Integer.parseInt(bookIdParam);
            int categoryId = Integer.parseInt(categoryIdParam);

            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM book_categories WHERE book_id = ? AND category_id = ?");

            st.setInt(1, bookId);
            st.setInt(2, categoryId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                // Пример получения данных из результата запроса
                int fetchedBookId = rs.getInt("book_id");
                int fetchedCategoryId = rs.getInt("category_id");

                // Создание JSON-ответа
                String jsonResponse = String.format("{\"book_id\": %d, \"category_id\": %d\"}",
                        fetchedBookId, fetchedCategoryId);

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Book category not found.");
            }

            rs.close();
            st.close();
            conn.close();

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format for book_id or category_id.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to retrieve book category.");
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equalsIgnoreCase(action)) {
            insertBookCategory(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteBookCategory(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action specified.");
        }
    }

    private BookCategory getBookCategoryById(int bookId, int categoryId) {
        BookCategory bookCategory = null;
        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM book_categories WHERE book_id=? AND category_id=?");
            st.setInt(1, bookId);
            st.setInt(2, categoryId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                bookCategory = new BookCategory();
                bookCategory.setBookId(rs.getInt("book_id"));
                bookCategory.setCategoryId(rs.getInt("category_id"));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookCategory;
    }

    private void insertBookCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String bookIdParam = request.getParameter("book_id");
            String categoryIdParam = request.getParameter("category_id");

            if (bookIdParam == null || categoryIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameters: book_id or category_id");
                return;
            }

            int bookId = Integer.parseInt(bookIdParam);
            int categoryId = Integer.parseInt(categoryIdParam);

            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)");

            st.setInt(1, bookId);
            st.setInt(2, categoryId);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Book category inserted successfully.");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format for book_id or category_id.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to insert book category.");
        }
    }

    private void deleteBookCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String bookIdParam = request.getParameter("book_id");
            String categoryIdParam = request.getParameter("category_id");

            if (bookIdParam == null || categoryIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameters: book_id or category_id");
                return;
            }

            int bookId = Integer.parseInt(bookIdParam);
            int categoryId = Integer.parseInt(categoryIdParam);

            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("DELETE FROM book_categories WHERE book_id = ? AND category_id = ?");

            st.setInt(1, bookId);
            st.setInt(2, categoryId);
            int rowsAffected = st.executeUpdate();

            st.close();
            conn.close();

            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Book category deleted successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("No matching book category found to delete.");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format for book_id or category_id.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete book category.");
        }
    }

}
