package org.example.repository.interf;

import org.example.models.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> findAll();
    Author findById(Integer id);
    Author save(Author author);
    Author update(Author author);
    void delete(Integer id);
}