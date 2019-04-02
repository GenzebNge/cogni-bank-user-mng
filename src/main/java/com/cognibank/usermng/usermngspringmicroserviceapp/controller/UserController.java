package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.*;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import io.swagger.annotations.*;

import java.util.Map;

@Api(tags = {"User API"})
@SwaggerDefinition(
        tags = {
                @Tag(name = "User API", description = "Cogni-Bank User API for creating users, authenticating them, reading and updating their properties")
        }
)
public interface UserController {
    String VERSION = "0.0.1";

    @ApiOperation(value = "Returns the current API version.", response = GetVersionResponse.class)
    GetVersionResponse getVersion();

    @ApiOperation(value = "Checks user credentials.", response = AuthenticatedUser.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful. Returns user ID, has email and has phone flags as a result."),
            @ApiResponse(code = 400, message = "Badly formed request, or validations failed."),
            @ApiResponse(code = 401, message = "Invalid user credentials."),
            @ApiResponse(code = 403, message = "When the user is locked.")
    })
    AuthenticatedUser checkUserNamePassword(
            @ApiParam(value = "User Credentials to Validate", required = true)
                    UserCredentials userCredentials);

    @ApiOperation(value = "Creates a user.", response = CreateUserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful. Returns user ID."),
            @ApiResponse(code = 400, message = "Badly formed request, or validations failed."),
            @ApiResponse(code = 406, message = "When username has already been registered.")
    })
    CreateUserResponse createUser(
            @ApiParam(value = "User Object to store in the database", required = true)
                    CreateUserRequest createUserRequest);

    @ApiOperation(value = "Update a user's details.", response = UpdateUserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 400, message = "Badly formed request, or validations failed."),
            @ApiResponse(code = 403, message = "First name and last name cannot be updated."),
            @ApiResponse(code = 404, message = "When user not found.")
    })
    UpdateUserResponse updateUser(String userId, Map<String, String> details);

    @ApiOperation(value = "Change a user's password.", response = ChangePasswordResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 400, message = "Badly formed request, or validations failed."),
            @ApiResponse(code = 403, message = "First name and last name cannot be updated."),
            @ApiResponse(code = 404, message = "When user not found.")
    })
    ChangePasswordResponse changePassword(String userId, ChangePasswordRequest request);

    @ApiOperation(value = "Unlock a user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 404, message = "When user not found.")
    })
    void unlockUser(String id);

    @ApiOperation(value = "Lock a user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 404, message = "When user not found.")
    })
    void lockUser(String id);

    @ApiOperation(value = "Get user details with user ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 404, message = "When user not found."),
            @ApiResponse(code = 403, message = "When the user is locked.")
    })
    Map<String, String> getUserDetails(String id);
}
