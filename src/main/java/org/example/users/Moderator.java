package org.example.users;

import jakarta.persistence.*;

@DiscriminatorValue("moderator")
@Entity
public class Moderator extends User {
    @Column(name = "moderator_role")
    private String moderatorRole;

    public String getModeratorRole() {
        return moderatorRole;
    }

    public void setModeratorRole(String moderatorRole) {
        this.moderatorRole = moderatorRole;
    }
}