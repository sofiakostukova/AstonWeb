package org.example.dao.interf;

import org.example.models.Admin;

import java.util.List;

public interface AdminDao {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);
    void saveAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(Integer id);
}