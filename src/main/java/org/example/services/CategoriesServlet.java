package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.Author;
import org.example.models.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CategoriesServlet", urlPatterns = "/api/categories")
public class CategoriesServlet extends HttpServlet {

    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Category.class, new CategoriesSerializer())
                .create();
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
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.getTransaction().commit();
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Category category = new Category();
            category.setName(name);
            session.save(category);
            session.getTransaction().commit();

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

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Category category = session.get(Category.class, id);
            category.setName(name);
            session.update(category);
            session.getTransaction().commit();

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

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.delete(category);
            session.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Category deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete category.");
        }
    }

    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}