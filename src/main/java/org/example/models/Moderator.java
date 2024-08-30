package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "moderators")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Moderator", discriminatorType = DiscriminatorType.STRING)
public class Moderator extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "moderator_role")
    private String moderatorRole;

    public Moderator(String moderatorRole) {
        this.moderatorRole = moderatorRole;
    }

    public Moderator(int id, String moderatorRole) {
        this.id = id;
        this.moderatorRole = moderatorRole;
    }
    public Moderator() {
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getModeratorRole() {
        return moderatorRole;
    }

    public void setModeratorRole(String moderatorRole) {
        this.moderatorRole = moderatorRole;
    }
}