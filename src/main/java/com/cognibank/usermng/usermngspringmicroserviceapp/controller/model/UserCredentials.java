package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import javax.validation.constraints.*;

public class UserCredentials {
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName.trim();
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password.trim();
    }
}
