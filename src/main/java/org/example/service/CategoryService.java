package org.example.service;

import org.example.models.Category;
import org.example.repository.impl.CategoryRepositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepositoryImpl categoryRepository;

    public CategoryService(CategoryRepositoryImpl repository) {
        this.categoryRepository = repository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        Category existingCategory = getCategoryById(category.getId());
        existingCategory.setName(category.getName());
        categoryRepository.update(existingCategory);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.delete(id);
    }
}