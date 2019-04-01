package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;

import java.util.stream.Collectors;

public class AuthenticatedUser {
    private String userId;
    private String phone;
    private boolean hasPhone;
    private String email;
    private boolean hasEmail;

    public AuthenticatedUser(User user) {
        setUserId(user.getId());
        user.getDetails().stream().
                collect(Collectors.toMap(UserDetails::getFieldName, UserDetails::getFieldValue))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals(UserService.EMAIL) || entry.getKey().equals(UserService.MOBILE_PHONE))
                .forEach(e -> {
                    switch (e.getKey()) {
                        case UserService.EMAIL:
                            setEmail(e.getValue());
                            setHasEmail();
                            break;
                        case UserService.MOBILE_PHONE:
                            setPhone(e.getValue());
                            setHasPhone();
                            break;
                    }
                });
    }

    public boolean getHasPhone() {
        return hasPhone;
    }

    private void setHasPhone() {
        this.hasPhone = true;
    }

    public boolean getHasEmail() {
        return hasEmail;
    }

    private void setHasEmail() {
        this.hasEmail = true;
    }

    public String getUserId() {
        return userId;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
