package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserRequest;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.GetVersionResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;

public interface UserController {
    String VERSION = "0.0.1";

    GetVersionResponse getVersion();

    AuthenticatedUser checkUserNamePassword(UserCredentials userCredentials);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);
}
