package org.example.repository.impl;

import org.example.models.Admin;
import org.example.models.Author;
import org.example.repository.interf.AdminRepository;
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
import java.util.Objects;

@Repository
public class AdminRepositoryImpl implements AdminRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Admin> findAll() {
        return jdbcTemplate.query("SELECT * FROM admins", new AdminRowMapper());
    }

    @Override
    public Admin findById(Integer id) {
        List<Admin> admins = jdbcTemplate.query("SELECT * FROM admins WHERE id = ?", new Object[]{id}, new AdminRowMapper());
        return admins.isEmpty() ? null : admins.get(0);
    }

    @Override
    public Admin save(Admin admin) {
        System.out.println("Saving entity with admin role: " + admin.getAdminRole());
        if (admin.getAdminRole() != null && !admin.getAdminRole().trim().isEmpty()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO admins (admin_type) VALUES (?)", new String[] {});
                ps.setString(1, admin.getAdminRole());
                return ps;
            }, keyHolder);
            Number key = keyHolder.getKey();
            if (key != null) {
                admin.setId(key.intValue());
            } else {
                throw new RuntimeException("Failed to retrieve generated key");
            }
        } else {
            throw new IllegalArgumentException("Admin role cannot be null or empty");
        }
        return admin;
    }

    @Override
    public Admin update(Admin admin) {
        jdbcTemplate.update("UPDATE admins SET admin_role = ? WHERE id = ?", admin.getAdminRole(), admin.getId());
        return admin;
    }

    @Override
    public void delete(Integer id) {
        Admin admin = findById(id);
        if (admin != null) {
            jdbcTemplate.update("DELETE FROM admins WHERE id = ?", id);
        }
    }

    private class AdminRowMapper implements RowMapper<Admin> {
        @Override
        public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
            Admin admin = new Admin();
            admin.setAdminRole(rs.getString("admin_role"));
            admin.setId(rs.getInt("id"));
            return admin;
        }
    }
}