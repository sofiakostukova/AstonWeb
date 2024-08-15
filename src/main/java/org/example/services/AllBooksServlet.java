package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.example.models.Book;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AllBooks", urlPatterns = "/api/AllBooks")
public class AllBooksServlet extends HttpServlet {

    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

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
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            books = session.createQuery("FROM Book", Book.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}