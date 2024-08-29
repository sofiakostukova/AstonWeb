package org.example.repository.impl;

import org.example.models.Author;
import org.example.models.Book;
import org.example.repository.interf.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    @Override
    public Book findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        System.out.println("Saving entity with title: " + book.getTitle());
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO books (title, publication_year, author_id, price) VALUES (?, ?, ?, ?)", new String[] {"id"});
                ps.setString(1, book.getTitle());
                ps.setInt(2, book.getPublicationYear());
                ps.setInt(3, book.getAuthor().getId());
                ps.setInt(4, book.getPrice());
                return ps;
            }, keyHolder);
            book.setId(keyHolder.getKey().intValue());
        } else {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            jdbcTemplate.update("UPDATE books SET title = ?, publication_year = ?, author_id = ?, price = ? WHERE id = ?", book.getTitle(), book.getPublicationYear(), book.getAuthor().getId(), book.getPrice(), book.getId());
        } else {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        return book;
    }

    @Override
    public void delete(Integer id) {
        Book book = findById(id);
        if (book != null) {
            jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
        }
    }

    private class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setPublicationYear(rs.getInt("publication_year"));
            book.setAuthor(new Author(rs.getInt("author_id")));
            book.setPrice(rs.getInt("price"));
            return book;
        }
    }
}