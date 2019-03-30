package com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;

import java.util.stream.Collectors;

public class ValidatedUser {
    private String userId;
    private boolean hasPhone;
    private boolean hasEmail;

    public ValidatedUser(User user) {
        if (null == user) {
            userId = null;
            return;
        }

        setUserId(user.getId());
        user.getDetails().stream().
                collect(Collectors.toMap(UserDetails::getFieldName, UserDetails::getFieldValue))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals(UserService.EMAIL) || entry.getKey().equals(UserService.MOBILE_PHONE))
                .forEach(e -> {
                    switch (e.getKey()) {
                        case UserService.EMAIL:
                            setHasEmail();
                            break;
                        case UserService.MOBILE_PHONE:
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
}
