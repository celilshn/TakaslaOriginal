package com.cengcelil.takaslaoriginal.Models;

import android.app.Application;

public class UserClient extends Application {
    private UserInformation userInformation = null;

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }
}
