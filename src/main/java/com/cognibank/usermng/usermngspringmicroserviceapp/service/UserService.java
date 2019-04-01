package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;

public interface UserService {
    String FIRST_NAME = "FirstName";
    String LAST_NAME = "LastName";
    String EMAIL = "Email";
    String MOBILE_PHONE = "MobilePhone";
    //String EMAIL_VALIDATED = "Email-isValidated";
    //String MOBILE_PHONE_VALIDATED = "MobilePhone-isValidated";

    String createNewUser(final User user);

    AuthenticatedUser authenticateUser(final String userName, final String password);
}
