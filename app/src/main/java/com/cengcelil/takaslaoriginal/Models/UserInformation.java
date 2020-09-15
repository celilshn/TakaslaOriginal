package com.cengcelil.takaslaoriginal.Models;

public class UserInformation {
    private String name,email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserInformation(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
