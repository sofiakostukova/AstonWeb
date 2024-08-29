package org.example.dao.interf;

import org.example.models.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Integer id);
}