package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModelProperty;

public class CreateUserResponse {
    @ApiModelProperty(notes = "Generated user ID.")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public CreateUserResponse withUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
