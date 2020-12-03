package com.example.application.Model;

public class User {
    private String username;
    private String password;
    private int id;
    private String email;
    public String getRole() {
        return role;
    }
    private String role;
    public User(int id,String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
