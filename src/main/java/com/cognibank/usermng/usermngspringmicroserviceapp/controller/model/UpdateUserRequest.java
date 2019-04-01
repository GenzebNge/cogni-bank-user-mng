package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

public class UpdateUserRequest {

    private String userId;

    private Map<String, String> details;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    private void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "userId='" + userId + '\'' +
                ", details=" + details +
                '}';
    }
}
