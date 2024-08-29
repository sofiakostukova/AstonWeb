package org.example.dao.interf;

import org.example.models.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();
    Book getBookById(Integer id);
    void saveBook(Book book);
    void updateBook(Book book);
    void deleteBook(Integer id);
}