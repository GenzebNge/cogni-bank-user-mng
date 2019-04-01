package com.cognibank.usermng.usermngspringmicroserviceapp.controller.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.UserController;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserRequest;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.GetVersionResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.util.UserTranslator;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserControllerImpl implements UserController {
    private UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/version")
    public GetVersionResponse getVersion() {
        return new GetVersionResponse().withVersion(VERSION);
    }

    @PostMapping("/checkUserNamePassword")
    public AuthenticatedUser checkUserNamePassword(@Valid @RequestBody UserCredentials userCredentials) {
        return userService.authenticateUser(userCredentials.getUserName(), userCredentials.getPassword());
    }

    @PostMapping("/createUser")
    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        System.out.println(createUserRequest);

        User newUser = UserTranslator.translate(createUserRequest);

        String userId = userService.createNewUser(newUser);

        return new CreateUserResponse()
                .withUserId(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
