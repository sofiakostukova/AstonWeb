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

@WebServlet(name = "CategoriesServlet", urlPatterns = "/api/categories")
public class CategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        Category category = getCategoryById(categoryId);

        if (category == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Category not found for id: " + categoryId);
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(category));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equalsIgnoreCase(action)) {
            insertCategory(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateCategory(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteCategory(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action specified.");
        }
    }

    private Category getCategoryById(int id) {
        Category category = null;
        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM categories WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO categories (name) VALUES (?)");

            st.setString(1, name);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Category inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to insert category.");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE categories SET name = ? WHERE id = ?");

            st.setString(1, name);
            st.setInt(2, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Category updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to update category.");
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName(DbUtil.driver);
            Connection conn = DriverManager.getConnection(DbUtil.url, DbUtil.user, DbUtil.password);
            PreparedStatement st = conn.prepareStatement("DELETE FROM categories WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            st.close();
            conn.close();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Category deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete category.");
        }
    }
}
