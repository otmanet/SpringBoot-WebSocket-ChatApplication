package com.example.AppChats.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name="email",unique = true)
    private String email;
    private String password;
    private boolean enabled;
    private String username;
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Collection<Role> roles;

    public User() {
        super();
    }

    public User(String email, String password, boolean enabled, String username, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.username = username;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
