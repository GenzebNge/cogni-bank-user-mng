package com.cognibank.usermng.usermngspringmicroserviceapp.controller.util;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.CreateUserRequest;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserTranslator {
    public static User translate(final CreateUserRequest createUserRequest) {
        final User newUser = new User()
                .withUserName(createUserRequest.getUserName())
                .withPassword(createUserRequest.getPassword())
                .withActive(false)
                .withType(UserType.User);

        List<UserDetails> details;
        if (null == createUserRequest.getDetails())
            details = new ArrayList<>();
        else
            details = createUserRequest.getDetails().entrySet().stream()
                    .map(e -> new UserDetails()
                            .withUser(newUser)
                            .withFieldName(e.getKey())
                            .withFieldValue(e.getValue()))
                    .collect(Collectors.toList());

        details.add(new UserDetails()
                .withUser(newUser)
                .withFieldName(UserService.FIRST_NAME)
                .withFieldValue(createUserRequest.getFirstName()));

        details.add(new UserDetails()
                .withUser(newUser)
                .withFieldName(UserService.LAST_NAME)
                .withFieldValue(createUserRequest.getLastName()));

        return newUser.withUserDetails(details);
    }
}
