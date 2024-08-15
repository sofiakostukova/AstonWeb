package org.example;

import org.example.models.Book;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


public class DatabaseTest {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> books = query.getResultList();

            for (Book book : books) {
                System.out.println("Book Title: " + book.getTitle());
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}