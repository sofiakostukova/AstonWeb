package org.example;

import org.example.models.Author;
import org.example.repository.impl.BookRepositoryImpl;
import org.example.service.BookService;
import org.example.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.*;

public class BookServiceTest {
    private BookService service;

    @Before
    public void setup() {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream("src/test/resources/application-test.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application-test.properties", e);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(props.getProperty("spring.datasource.url"));
        dataSource.setUser(props.getProperty("spring.datasource.username"));
        dataSource.setPassword(props.getProperty("spring.datasource.password"));

        BookRepositoryImpl repository = new BookRepositoryImpl(dataSource);
        service = new BookService(repository);
    }

    @Test
    public void testCreateBook() {
        Book book = new Book("Test book", 2022, new Author(2), 200, new ArrayList<>());
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            Book savedBook = service.saveBook(book);
            assertNotNull(savedBook);
            assertEquals("Test book", savedBook.getTitle());
        } else {
            fail("Book is null or book title is null or empty");
        }
    }

    @Test
    public void testUpdateBook() {
        Book book = service.getBookById(8);
        assertNotNull(book);
        book.setTitle("Updated Test book");
        service.updateBook(book);
        Book updatedBook = service.getBookById(8);
        assertNotNull(updatedBook);
        assertEquals("Updated Test book", updatedBook.getTitle());
    }

    @Test
    public void testReadBook() {
        Book book = service.getBookById(8);
        assertNotNull(book);
        assertEquals("Updated Test book", book.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Book bookBeforeDeletion = service.getBookById(8);
        assertNotNull(bookBeforeDeletion);
        service.deleteBook(8);
    }
}