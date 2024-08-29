package org.example.service;

import org.example.models.Book;
import org.example.repository.interf.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository repository) {
        this.bookRepository = repository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        bookRepository.update(existingBook);
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }
}