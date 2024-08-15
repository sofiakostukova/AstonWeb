package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.Author;
import org.example.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthorsServlet", urlPatterns = "/api/authors")
public class AuthorsServlet extends HttpServlet {
    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int authorId = Integer.parseInt(request.getParameter("id"));
            Author author = getAuthorById(authorId);

            if (author == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Author not found for id: " + authorId);
                return;
            }

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Author.class, new AuthorSerializer())
                    .create();
            out.print(gson.toJson(author));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error occurred while processing request: " + e.getMessage());
        }
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
/*
    private Author getAuthorById(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Author> query = session.createQuery("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id", Author.class);
            query.setParameter("id", id);
            Author author = query.uniqueResult();
            session.getTransaction().commit();
            return author;
        } catch (Exception e) {
            System.out.println("Error getting author by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }*/
    private Author getAuthorById(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            System.out.println("Getting author by ID: " + id);
            Author author = session.get(Author.class, id);
            System.out.println("Author found: " + author);
            session.getTransaction().commit();
            return author;
        } catch (Exception e) {
            System.out.println("Error getting author by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void insertAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Author author = new Author();
            author.setName(name);
            session.save(author);
            session.getTransaction().commit();

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

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Author author = session.get(Author.class, id);
            author.setName(name);
            session.update(author);
            session.getTransaction().commit();

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

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Author author = session.get(Author.class, id);
            session.delete(author);
            session.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Author deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete author.");
        }
    }

    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}