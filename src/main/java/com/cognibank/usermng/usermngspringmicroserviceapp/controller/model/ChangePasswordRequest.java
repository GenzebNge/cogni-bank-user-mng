package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {
    @ApiModelProperty(notes = "Password must be between 8-32 characters.", position = 2)
    @NotBlank(message = "{CreateUser.Password.Blank}")
    @Size(min = 8, max = 32, message = "{CreateUser.Password.Size}")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordRequest withNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
