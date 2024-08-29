package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.dao.interf.CategoryDao;
import org.example.models.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> getAllCategories() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public void saveCategory(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void updateCategory(Category category) {
        entityManager.merge(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = getCategoryById(id);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}