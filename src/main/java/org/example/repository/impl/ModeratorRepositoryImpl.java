package org.example.repository.impl;

import org.example.models.Moderator;
import org.example.repository.interf.GenericRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Repository
public class ModeratorRepositoryImpl implements GenericRepository<Moderator> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ModeratorRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Moderator> findAll() {
        return jdbcTemplate.query("SELECT * FROM moderators", new ModeratorRowMapper());
    }

    @Override
    public Moderator findById(Integer id) {
        List<Moderator> moderators = jdbcTemplate.query("SELECT * FROM moderators WHERE id = ?", new Object[]{id}, new ModeratorRowMapper());
        return moderators.isEmpty() ? null : moderators.get(0);
    }

    @Override
    public Moderator save(Moderator moderator) {
        System.out.println("Saving entity with moderator role: " + moderator.getModeratorRole());
        if (moderator.getModeratorRole() != null && !moderator.getModeratorRole().trim().isEmpty()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public @NotNull PreparedStatement createPreparedStatement(@NotNull Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO moderators (moderator_role) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, moderator.getModeratorRole());
                    return ps;
                }
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys != null && keys.size() > 0) {
                moderator.setId((Integer) keys.get("id"));
            } else {
                throw new RuntimeException("Failed to retrieve generated key");
            }
        } else {
            throw new IllegalArgumentException("Moderator role cannot be null or empty");
        }
        return moderator;
    }

    @Override
    public Moderator update(Moderator moderator) {
        jdbcTemplate.update("UPDATE moderators SET moderator_role = ? WHERE id = ?", moderator.getModeratorRole(), moderator.getId());
        return moderator;
    }

    @Override
    public void delete(Integer id) {
        Moderator moderator = findById(id);
        if (moderator != null) {
            jdbcTemplate.update("DELETE FROM moderators WHERE id = ?", id);
        }
    }

    private class ModeratorRowMapper implements RowMapper<Moderator> {
        @Override
        public Moderator mapRow(ResultSet rs, int rowNum) throws SQLException {
            Moderator moderator = new Moderator();
            moderator.setModeratorRole(rs.getString("moderator_role"));
            moderator.setId(rs.getInt("id"));
            return moderator;
        }
    }
}