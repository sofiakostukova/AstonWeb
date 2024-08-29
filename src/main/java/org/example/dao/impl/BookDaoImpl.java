package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.dao.interf.BookDao;
import org.example.models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public Book getBookById(Integer id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    public void updateBook(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void deleteBook(Integer id) {
        Book book = getBookById(id);
        if (book != null) {
            entityManager.remove(book);
        }
    }
}