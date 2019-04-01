package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

public class UpdateUserResponse {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public UpdateUserResponse withUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
