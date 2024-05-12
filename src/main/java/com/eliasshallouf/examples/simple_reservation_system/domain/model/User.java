package com.eliasshallouf.examples.simple_reservation_system.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String githubEmail;
    @Column
    private Boolean isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setGithubEmail(String githubEmail) {
        this.githubEmail = githubEmail;
    }

    public String getGithubEmail() {
        return githubEmail;
    }
}
