package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "User credentials object should have following fields ")
public class UserCredentials {
    @ApiModelProperty(notes = "User name, length must be 3-32 chars.", position = 1)
    @NotBlank(message = "{CreateUser.UserName.Blank}")
    @Size(min = 3, max = 32, message = "{CreateUser.UserName.Size}")
    private String userName;

    @ApiModelProperty(notes = "Password, length must be 8-32 chars.", position = 2)
    @NotBlank(message = "{CreateUser.Password.Blank}")
    @Size(min = 8, max = 32, message = "{CreateUser.Password.Size}")
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
