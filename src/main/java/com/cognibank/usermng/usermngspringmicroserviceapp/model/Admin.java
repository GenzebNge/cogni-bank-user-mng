package com.cognibank.usermng.usermngspringmicroserviceapp.model;

public class Admin {
    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Admin username(String username) {
        this.username = username;
        return this;
    }

    public Admin password(String password) {
        this.password = password;
        return this;
    }


}
