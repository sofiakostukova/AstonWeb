package org.example;

import org.example.models.Category;
import org.example.repository.impl.CategoryRepositoryImpl;
import org.example.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class CategoryServiceTest {
    private CategoryService service;

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

        CategoryRepositoryImpl repository = new CategoryRepositoryImpl(dataSource);
        service = new CategoryService(repository);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category("Test category");
        if (category.getName() != null && !category.getName().trim().isEmpty()) {
            Category savedCategory = service.saveCategory(category);
            assertNotNull(savedCategory);
            assertEquals("Test category", savedCategory.getName());
        } else {
            fail("Category is null or category name is null or empty");
        }
    }

    @Test
    public void testUpdateCategory() {
        Category category = service.getCategoryById(5);
        assertNotNull(category);
        category.setName("Updated Test category");
        service.updateCategory(category);
        Category updatedCategory = service.getCategoryById(5);
        assertNotNull(updatedCategory);
        assertEquals("Updated Test category", updatedCategory.getName());
    }

    @Test
    public void testReadCategory() {
        Category category = service.getCategoryById(5);
        assertNotNull(category);
        assertEquals("Updated Test category", category.getName());
    }

    @Test
    public void testDeleteCategory() {
        Category categoryBeforeDeletion = service.getCategoryById(5);
        assertNotNull(categoryBeforeDeletion);
        service.deleteCategory(5);
    }
}