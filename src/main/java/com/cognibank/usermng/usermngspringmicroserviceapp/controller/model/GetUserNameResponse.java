package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModelProperty;

public class GetUserNameResponse {

    @ApiModelProperty(notes = "User name mapped to email id.")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public GetUserNameResponse withUserName(String userName) {
        setUserName(userName);
        return this;
    }
}
