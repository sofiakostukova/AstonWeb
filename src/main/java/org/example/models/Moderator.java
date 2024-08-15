package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "moderators")
@DiscriminatorValue("Moderator")
@PrimaryKeyJoinColumn(name = "id")
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