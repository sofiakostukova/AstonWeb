package org.example;

import org.example.models.Admin;
import org.example.repository.impl.AdminRepositoryImpl;
import org.example.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class AdminServiceTest {
    private AdminService service;

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

        AdminRepositoryImpl repository = new AdminRepositoryImpl(dataSource);
        service = new AdminService(repository);
    }

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin( "Test admin role");
        if (admin.getAdminRole() != null && !admin.getAdminRole().trim().isEmpty()) {
            Admin savedAdmin = service.saveAdmin(admin);
            assertNotNull(savedAdmin);
            assertEquals("Test admin role", savedAdmin.getAdminRole());
        } else {
            fail("Admin is null or admin role is null or empty");
        }
    }

    @Test
    public void testUpdateAdmin() {
        Admin admin = service.getAdminById(2);
        assertNotNull(admin);
        admin.setAdminRole("Updated Test admin role");
        service.updateAdmin(admin);
        Admin updatedAdmin = service.getAdminById(2);
        assertNotNull(updatedAdmin);
        assertEquals("Updated Test admin role", updatedAdmin.getAdminRole());
    }

    @Test
    public void testReadAdmin() {
        Admin admin = service.getAdminById(2);
        assertNotNull(admin);
        assertEquals("Updated Test admin role", admin.getAdminRole());
    }

    @Test
    public void testDeleteAdmin() {
        Admin adminBeforeDeletion = service.getAdminById(3);
        assertNotNull(adminBeforeDeletion);
        service.deleteAdmin(3);
    }
}