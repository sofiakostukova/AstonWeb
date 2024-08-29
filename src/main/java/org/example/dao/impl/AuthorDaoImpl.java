package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.dao.interf.AuthorDao;
import org.example.models.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> getAllAuthors() {
        return entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Author getAuthorById(Integer id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public void saveAuthor(Author author) {
        entityManager.persist(author);
    }

    @Override
    public void updateAuthor(Author author) {
        entityManager.merge(author);
    }

    @Override
    public void deleteAuthor(Integer id) {
        Author author = getAuthorById(id);
        if (author != null) {
            entityManager.remove(author);
        }
    }
}