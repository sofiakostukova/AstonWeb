package org.example.service;

import org.example.models.Admin;
import org.example.repository.interf.AdminRepository;

import java.util.List;

public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository repository) {
        this.adminRepository = repository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void updateAdmin(Admin admin) {
        Admin existingAdmin = getAdminById(admin.getId());
        existingAdmin.setAdminRole(admin.getAdminRole());
        adminRepository.update(existingAdmin);
    }

    public void deleteAdmin(Integer id) {
        adminRepository.delete(id);
    }
}