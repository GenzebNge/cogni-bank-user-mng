package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

public class CreateUserResponse {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public CreateUserResponse withUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
