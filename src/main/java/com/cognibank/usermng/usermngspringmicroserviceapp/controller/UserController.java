package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.*;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Management System", tags = "Operations pertaining to User in User Management System")
public interface UserController {
    String VERSION = "0.0.1";

    @ApiOperation(value = "Endpoint which can get you the Version", notes = "", response = GetVersionResponse.class)
    GetVersionResponse getVersion();

    @ApiOperation(value = "End Point to post a User to User Management", notes = "", response = AuthenticatedUser.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated."),
            @ApiResponse(code = 401, message = "Invalid credentials."),
            @ApiResponse(code = 403, message = "User is locked.")
    })
    AuthenticatedUser checkUserNamePassword(UserCredentials userCredentials);

    @ApiOperation(value = "End Point to post a User to User Management", notes = "", response = CreateUserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 406, message = "When username has already been registered.")
    })
    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

    void unlockUser(String id);
}
