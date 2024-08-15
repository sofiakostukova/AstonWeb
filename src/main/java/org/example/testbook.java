package org.example;

import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "testbook", urlPatterns = "/testbook")
public class testbook extends HttpServlet {

    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("FROM Book").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to connect to the database", e);
        }
    }

}