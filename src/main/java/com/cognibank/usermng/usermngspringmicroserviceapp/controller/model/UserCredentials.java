package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

@ApiModel(value = "User credentials object should have following fields ")
public class UserCredentials {

    @NotBlank
    @ApiModelProperty(value = "UserName given by the User", required = true, position = 1)
    private String userName;
    @NotBlank
    @Size(min = 8, max = 32)
    @ApiModelProperty(value = "Password given by the User with size between 8 & 32 Characters inclusives", required = true, position = 2)
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
