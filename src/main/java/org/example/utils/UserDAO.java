package org.example.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {
    @PersistenceContext
    public EntityManager em;

    public List<User> findAllUsers() {
        return em.createNamedQuery("findAllUsers", User.class).getResultList();
    }

    public User findUserByUsername(String username) {
        return em.createNamedQuery("findUserByUsername", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public void saveUser(User user) {
        em.persist(user);
    }

    public void updateUser(User user) {
        em.merge(user);
    }

    public void deleteUser(int id) {
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from User u where u.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public User findUserById(int id) {
        return em.find(User.class, id);
    }
}