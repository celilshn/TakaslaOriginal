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
    private float rate = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

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
