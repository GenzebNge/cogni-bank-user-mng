package com.cognibank.usermng.usermngspringmicroserviceapp.controller.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.UserController;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.*;
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
import java.util.Map;
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
    public AuthenticatedUser checkUserNamePassword(
            @Valid @RequestBody UserCredentials userCredentials) {
        return userService.authenticateUser(userCredentials.getUserName(), userCredentials.getPassword());
    }

    @PostMapping("/createUser")
    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        final User newUser = UserTranslator.translate(createUserRequest);

        final String userId = userService.createNewUser(newUser);

        return new CreateUserResponse()
                .withUserId(userId);
    }

    @PutMapping("/updateUser/{userId}")
    public UpdateUserResponse updateUser(@PathVariable String userId, @RequestBody Map<String, String> details) {
        final boolean updateStatus = userService.updateUser(userId, details);
        return new UpdateUserResponse().withUpdated(updateStatus);
    }

    @PutMapping("/changePassword/{userId}")
    public ChangePasswordResponse changePassword(@PathVariable String userId, @Valid @RequestBody ChangePasswordRequest request) {
        final boolean changeStatus = userService.changePassword(userId, request.getNewPassword());
        return new ChangePasswordResponse().withChanged(changeStatus);
    }

    @PutMapping("/unlockUser/{userId}")
    public void unlockUser(@PathVariable String userId) {
        userService.unlockUser(userId);
    }

    @PutMapping("/lockUser/{userId}")
    public void lockUser(@PathVariable String userId) {
        userService.lockUser(userId);
    }

    @GetMapping("/getUserDetails/{userId}")
        public Map<String, String> getUserDetails(@PathVariable String userId) {
            return userService.getUserDetails(userId);
    }


    @GetMapping("/retrieveUserId/{userName}")
    public CreateUserResponse getUserIdFromUserName(@PathVariable String userName) {
        final String userId = userService.getUserId(userName);
        return new CreateUserResponse().withUserId(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
//        System.out.println("User is deleted");

    }


}

