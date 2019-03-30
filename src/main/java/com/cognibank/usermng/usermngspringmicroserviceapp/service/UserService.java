package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.ValidatedUser;

public interface UserService {
    String FIRST_NAME = "FirstName";
    String LAST_NAME = "LastName";
    String EMAIL = "Email";
    String MOBILE_PHONE = "MobilePhone";

    String createNewUser(final User user);

    ValidatedUser validateUser(final String userName, final String password);
}
