package org.example.repository.impl;

import org.example.models.Category;
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
public class CategoryRepositoryImpl implements GenericRepository<Category> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM categories", new CategoryRowMapper());
    }

    @Override
    public Category findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM categories WHERE id = ?", new Object[]{id}, new CategoryRowMapper());
    }

    @Override
    public Category save(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)", new String[] {"id"});
            ps.setString(1, category.getName());
            return ps;
        }, keyHolder);
        category.setId(keyHolder.getKey().intValue());
        return category;
    }

    @Override
    public Category update(Category category) {
        jdbcTemplate.update("UPDATE categories SET name = ? WHERE id = ?", category.getName(), category.getId());
        return category;
    }

    @Override
    public void delete(Integer id) {
        Category category = findById(id);
        if (category != null) {
            jdbcTemplate.update("DELETE FROM categories WHERE id = ?", id);
        }
    }

    private class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category(rs.getString("name"));
            category.setId(rs.getInt("id"));
            return category;
        }
    }
}