package com.studentManagementSystem.studentManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an application user for authentication and authorization purposes.
 * This is kept separate from the Student entity to distinguish between
 * a person in the system and a user who can log in.
 */
@Entity
@Table(name = "app_user") // "user" is often a reserved keyword in SQL
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // This will store the HASHED password

    @Column(nullable = false)
    private String roles; // e.g., "ROLE_USER,ROLE_ADMIN"

    public User() {
    }

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}