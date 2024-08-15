package org.example.users;

import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Admin> adminQuery = session.createQuery("FROM Admin", Admin.class);
            List<Admin> admins = adminQuery.getResultList();

            Query<Moderator> moderatorQuery = session.createQuery("FROM Moderator", Moderator.class);
            List<Moderator> moderators = moderatorQuery.getResultList();

            Query<Customer> customerQuery = session.createQuery("FROM Customer", Customer.class);
            List<Customer> customers = customerQuery.getResultList();

            List<User> users = new ArrayList<User>();
            users.addAll(admins);
            users.addAll(moderators);
            users.addAll(customers);

            transaction.commit();
            return users;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}