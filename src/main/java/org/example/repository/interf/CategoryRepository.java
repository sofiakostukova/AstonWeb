package org.example.repository.interf;

import org.example.models.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(Integer id);
    Category save(Category category);
    Category update(Category category);
    void delete(Integer id);
}