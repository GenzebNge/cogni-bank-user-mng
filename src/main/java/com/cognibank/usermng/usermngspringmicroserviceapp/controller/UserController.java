package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.NewUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;

public interface UserController {
    String VERSION = "0.0.1";

    String getVersion();

    AuthenticatedUser checkUserNamePassword(UserCredentials userCredentials);

    void createUser(NewUser newUser);
}
