package org.example.repository.interf;

import java.util.List;

public interface GenericRepository<T> {
    List<T> findAll();
    T findById(Integer id);
    T save(T entity);
    T update(T entity);
    void delete(Integer id);
}