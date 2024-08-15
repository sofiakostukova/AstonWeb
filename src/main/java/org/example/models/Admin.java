package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "admins")
@DiscriminatorValue("Admin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    @Column(name = "admin_role")
    private String adminRole;

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }
}