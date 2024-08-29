package org.example;

import org.example.repository.impl.AuthorRepositoryImpl;
import org.example.service.AuthorService;
import org.example.models.Author;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;


public class AuthorServiceTest {
    private AuthorService service;

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

        AuthorRepositoryImpl repository = new AuthorRepositoryImpl(dataSource);
        service = new AuthorService(repository);
    }

    @Test
    public void testCreateAuthor() {
        Author author = new Author("Test author");
        if (author.getName() != null && !author.getName().trim().isEmpty()) {
            Author savedAuthor = service.saveAuthor(author);
            assertNotNull(savedAuthor);
            assertEquals("Test author", savedAuthor.getName());
        } else {
            fail("Author is null or author name is null or empty");
        }
    }

    @Test
    public void testUpdateAuthor() {
        Author author = service.getAuthorById(25);
        assertNotNull(author);
        author.setName("Updated Test author");
        service.updateAuthor(author);
        Author updatedAuthor = service.getAuthorById(25);
        assertNotNull(updatedAuthor);
        assertEquals("Updated Test author", updatedAuthor.getName());
    }

    @Test
    public void testReadAuthor() {
        Author author = service.getAuthorById(25);
        assertNotNull(author);
        assertEquals("Test author", author.getName());
    }


    @Test
    public void testDeleteAuthor() {
        Author authorBeforeDeletion = service.getAuthorById(24);
        assertNotNull(authorBeforeDeletion);
        service.deleteAuthor(24);

    }
}