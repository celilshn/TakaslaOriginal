package com.cengcelil.takaslaoriginal.Models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserInformation {
    public UserInformation() {
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    private String name, email;

    public Date getLastLogin() {
        return lastLogin;
    }

    @ServerTimestamp
    Date lastLogin;
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
