package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.NewUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.validator.CreateUserValidator;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.ValidatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserController {
    String VERSION = "1.0.0";

    String getVersion();

    ValidatedUser checkUserNamePassword(UserCredentials userCredentials);

    void createUser(NewUser newUser);
}
