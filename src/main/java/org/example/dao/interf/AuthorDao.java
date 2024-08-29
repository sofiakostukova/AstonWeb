package org.example.dao.interf;

import org.example.models.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAllAuthors();
    Author getAuthorById(Integer id);
    void saveAuthor(Author author);
    void updateAuthor(Author author);
    void deleteAuthor(Integer id);
}
