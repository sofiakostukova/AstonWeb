package org.example.repository.impl;

import org.example.models.Customer;
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
public class CustomerRepositoryImpl implements GenericRepository<Customer> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new CustomerRowMapper());
    }

    @Override
    public Customer findById(Integer id) {
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers WHERE id = ?", new Object[]{id}, new CustomerRowMapper());
        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    public Customer save(Customer customer) {
        System.out.println("Saving entity with customer type: " + customer.getCustomerType());
        if (customer.getCustomerType() != null && !customer.getCustomerType().trim().isEmpty()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public @NotNull PreparedStatement createPreparedStatement(@NotNull Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO customers (customer_type) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, customer.getCustomerType());
                    return ps;
                }
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys != null && keys.size() > 0) {
                customer.setId((Integer) keys.get("id"));
            } else {
                throw new RuntimeException("Failed to retrieve generated key");
            }
        } else {
            throw new IllegalArgumentException("Customer type cannot be null or empty");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        jdbcTemplate.update("UPDATE customers SET customer_type = ? WHERE id = ?", customer.getCustomerType(), customer.getId());
        return customer;
    }

    @Override
    public void delete(Integer id) {
        Customer customer = findById(id);
        if (customer != null) {
            jdbcTemplate.update("DELETE FROM customers WHERE id = ?", id);
        }
    }

    private class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerType(rs.getString("customer_type"));
            customer.setId(rs.getInt("id"));
            return customer;
        }
    }
}