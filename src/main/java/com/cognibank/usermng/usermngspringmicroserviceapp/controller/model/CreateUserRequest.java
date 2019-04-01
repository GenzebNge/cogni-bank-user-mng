package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.util.Map;

public class CreateUserRequest {
    @ApiModelProperty(notes = "User name must be unique and between 3-32 characters.", position = 1)
    @NotBlank(message = "{CreateUser.UserName.Blank}")
    @Size(min = 3, max = 32, message = "{CreateUser.UserName.Size}")
    @Pattern.List({
            @Pattern(regexp = "^[a-zA-Z].*$", message = "{CreateUser.UserName.PatternStartWithLetters}"),
            @Pattern(regexp = "^[a-zA-Z0-9_/.]+$", message = "{CreateUser.UserName.PatternValidCharacters}")
    })
    private String userName;

    @ApiModelProperty(notes = "Password must be between 8-32 characters.", position = 2)
    @NotBlank(message = "{CreateUser.Password.Blank}")
    @Size(min = 8, max = 32, message = "{CreateUser.Password.Size}")
    private String password;

    @ApiModelProperty(notes = "A well formed email.", position = 3)
    @Email(message = "{CreateUser.Email.Invalid}")
    @NotNull(message = "{CreateUser.Email.Blank}")
    private String email;

    @ApiModelProperty(notes = "First name is required.", position = 4)
    @NotBlank(message = "{CreateUser.FirstName.Blank}")
    @Size(min = 2, max = 255, message = "{CreateUser.FirstName.Size}")
    private String firstName;

    @ApiModelProperty(notes = "Last name is required.", position = 5)
    @NotBlank(message = "{CreateUser.LastName.Blank}")
    @Size(min = 2, max = 255, message = "{CreateUser.LastName.Size}")
    private String lastName;

    @ApiModelProperty(notes = "Additional details for the user <String, String>.", allowEmptyValue = true, position = 99,
            example = "{\"PhoneNumber\": \"773-123-4567\", \"Home.Street\": \"1234 Some Str\", \"Home.City\": \"Charlotte\", \"Home.State\": \"NC\", \"<Any Key>\": \"<Any Value>\"}")
    private Map<String, String> details;

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    private void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", details=" + details +
                '}';
    }
}
