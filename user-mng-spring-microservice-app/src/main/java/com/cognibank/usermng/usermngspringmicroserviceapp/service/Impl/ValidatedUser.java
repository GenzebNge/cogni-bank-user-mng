package com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class ValidatedUser {
    private String userId;
    private boolean hasPhone;
    private boolean hasEmail;

    public ValidatedUser(User user, List<UserDetails> details) {
        setUserId(user.getId());
        details.stream().
                collect(Collectors.toMap(UserDetails::getFieldName, UserDetails::getFieldValue))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals(UserServiceImpl.EMAIL) || entry.getKey().equals(UserServiceImpl.MOBILE_PHONE))
                .forEach(e -> {
                    switch (e.getKey()) {
                        case UserServiceImpl.EMAIL:
                            setHasEmail();
                            break;
                        case UserServiceImpl.MOBILE_PHONE:
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
