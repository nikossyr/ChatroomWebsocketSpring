package com.example.chatroom.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "messaging_service")
public class User {
    private int userId;
    private String Username;
    private String password;
    private String role;
    private Timestamp userCreated;

    @Id
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_USERNAME")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String userUsername) {
        this.Username = userUsername;
    }

    @Basic
    @Column(name = "USER_PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String userPassword) {
        this.password = userPassword;
    }

    @Basic
    @Column(name = "USER_ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String userRole) {
        this.role = userRole;
    }

    @Basic
    @Column(name = "USER_CREATED")
    public Timestamp getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Timestamp userCreated) {
        this.userCreated = userCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(Username, user.Username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role) &&
                Objects.equals(userCreated, user.userCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, Username, password, role, userCreated);
    }
}
