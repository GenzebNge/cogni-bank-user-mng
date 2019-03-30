package com.cognibank.usermng.usermngspringmicroserviceapp.controller.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.UserController;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.NewUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.validator.CreateUserValidator;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.ValidatedUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserControllerImpl implements UserController {
    private UserService userService;
    private CreateUserValidator createUserValidator;

    @Autowired
    public UserControllerImpl(UserService userService, CreateUserValidator createUserValidator) {
        this.userService = userService;
        this.createUserValidator = createUserValidator;
    }

    @InitBinder("newUser")
    public void initMerchantOnlyBinder(WebDataBinder binder) {
        binder.addValidators(createUserValidator);
    }

    @GetMapping("/version")
    public String getVersion() {
        return VERSION;
    }

    @PostMapping("/checkUserNamePassword")
    public ValidatedUser checkUserNamePassword(@Valid @RequestBody UserCredentials userCredentials) {
        return userService.validateUser(userCredentials.getUserName(), userCredentials.getPassword());
    }

    @PostMapping("/createUser")
    public void createUser(@Valid @RequestBody NewUser newUser) {
        System.out.println(newUser);
        String userId = userService.createNewUser(null);

        throw new RuntimeException("Not Implemented");
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
