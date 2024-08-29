package org.example.repository.interf;

import org.example.models.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    Book findById(Integer id);
    Book save(Book book);
    Book update(Book book);
    void delete(Integer id);
}