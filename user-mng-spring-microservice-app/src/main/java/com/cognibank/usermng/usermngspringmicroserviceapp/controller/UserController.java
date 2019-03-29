package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl.ValidatedUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import formdata.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    static final String VERSION = "1.0.0";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/version")
    public String getVersion() {
        return VERSION;
    }

    @PostMapping("/checkUserNamePassword")
    public ValidatedUser checkUserNamePassword(@RequestBody UserCredentials userCredentials) {
        return userService.validateUser(userCredentials.userName, userCredentials.password);
    }
}
