package org.example.dao.impl;

import org.example.dao.interf.AdminDao;
import org.example.models.Admin;
import org.example.repository.interf.AdminRepository;

import java.util.List;

public class AdminDaoImpl implements AdminDao {
    private AdminRepository adminRepository;

    public AdminDaoImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    @Override
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminRepository.update(admin);
    }

    @Override
    public void deleteAdmin(Integer id) {
        adminRepository.delete(id);
    }
}