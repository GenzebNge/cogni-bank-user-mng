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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Api(value = "User Management System", tags = "Operations pertaining to User in User Management System")
public class UserControllerImpl implements UserController {
    private UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Endpoint which can get you the Version",notes = "", response = GetVersionResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/version")
    public GetVersionResponse getVersion() {
        return new GetVersionResponse().withVersion(VERSION);
    }

    @ApiOperation(value ="End Point to post a User to User Management", notes = "", response = AuthenticatedUser.class)

    @PostMapping("/checkUserNamePassword")
    public AuthenticatedUser checkUserNamePassword(@ApiParam(value = "User Object to store in the database", required = true) @Valid @RequestBody UserCredentials userCredentials) {
        return userService.authenticateUser(userCredentials.getUserName(), userCredentials.getPassword());
    }

    @ApiOperation(value ="End Point to post a User to User Management", notes = "", response = CreateUserResponse.class)

    @PostMapping("/createUser")
    public CreateUserResponse createUser(@ApiParam(value = "User Credentials to Validate", required = true) @Valid @RequestBody CreateUserRequest createUserRequest) {
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
