package org.example.repository.impl;

import org.example.models.Author;

import org.example.repository.interf.GenericRepository;
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
public class AuthorRepositoryImpl implements GenericRepository<Author> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM authors", new AuthorRowMapper());
    }

    @Override
    public Author findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM authors WHERE id = ?", new Object[]{id}, new AuthorRowMapper());
    }

    @Override
    public Author save(Author author) {
        System.out.println("Saving entity with name: " + author.getName());
        if (author.getName() != null && !author.getName().trim().isEmpty()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO authors (name) VALUES (?)", new String[] {"id"});
                ps.setString(1, author.getName());
                return ps;
            }, keyHolder);
            author.setId(keyHolder.getKey().intValue());
        } else {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        return author;
    }

    @Override
    public Author update(Author author) {
        if (author.getName() != null && !author.getName().trim().isEmpty()) {
            jdbcTemplate.update("UPDATE authors SET name = ? WHERE id = ?", author.getName(), author.getId());
        } else {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        return author;
    }

    @Override
    public void delete(Integer id) {
        Author author = findById(id);
        if (author != null) {
            jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
        }
    }

    private class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            return author;
        }
    }
}