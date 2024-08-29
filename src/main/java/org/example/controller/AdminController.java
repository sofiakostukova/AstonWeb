package org.example.controller;

import org.example.models.Admin;
import org.example.service.AdminService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService service) {
        this.adminService = service;
    }

    @GetMapping
    public String getAllAdmins(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admins";
    }

    @GetMapping("/{id}")
    public String getAdminById(@PathVariable Integer id, Model model) {
        model.addAttribute("admin", adminService.getAdminById(id));
        return "admin";
    }

    @PostMapping
    public String saveAdmin(@ModelAttribute Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admins";
    }

    @PutMapping("/{id}")
    public String updateAdmin(@PathVariable Integer id, @ModelAttribute Admin admin) {
        admin.setId(id);
        adminService.updateAdmin(admin);
        return "redirect:/admins";
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return "redirect:/admins";
    }
}