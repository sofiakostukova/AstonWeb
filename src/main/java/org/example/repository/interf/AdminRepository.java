package org.example.repository.interf;

import org.example.models.Admin;

import java.util.List;

public interface AdminRepository {
    List<Admin> findAll();
    Admin findById(Integer id);
    Admin save(Admin admin);
    Admin update(Admin admin);
    void delete(Integer id);
}