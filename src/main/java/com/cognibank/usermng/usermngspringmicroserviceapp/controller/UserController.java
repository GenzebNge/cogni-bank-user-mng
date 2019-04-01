package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserRequest;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.GetVersionResponse;
import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.UserCredentials;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import io.swagger.annotations.*;

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
            @ApiResponse(code = 403, message = "Credentials are invalid.")
    })
    AuthenticatedUser checkUserNamePassword(UserCredentials userCredentials);

    @ApiOperation(value = "Creates a user.", response = CreateUserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful. Returns user ID."),
            @ApiResponse(code = 400, message = "Badly formed request, or validations failed.")
    })
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
}
