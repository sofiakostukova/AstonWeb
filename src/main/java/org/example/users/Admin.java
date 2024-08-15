package org.example.users;

import jakarta.persistence.*;

@DiscriminatorValue("admin")
@Entity
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