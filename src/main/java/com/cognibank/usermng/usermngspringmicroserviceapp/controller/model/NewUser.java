package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

public class NewUser {
    @NotBlank(message = "{CreateUser.UserName.Blank}")
    @Size(min = 3, max = 32, message = "{CreateUser.UserName.Size}")
    @Pattern.List({
            @Pattern(regexp = "^[a-zA-Z]", message = "{CreateUser.UserName.PatternStartWithLetters}"),
            @Pattern(regexp = "^[a-zA-Z0-9_/.]+$", message = "{CreateUser.UserName.PatternValidCharacters}")
    })
    private String userName;
    @NotBlank(message = "{CreateUser.Password.Blank}")
    @Size(min = 8, max = 32, message = "{CreateUser.Password.Size}")
    private String password;
    @Email(message = "{CreateUser.Email.Invalid}")
    private String email;
    @NotBlank(message = "{CreateUser.FirstName.Blank}")
    @Size(min = 2, max = 255, message = "{CreateUser.FirstName.Size}")
    private String firstName;
    @NotBlank(message = "{CreateUser.LastName.Blank}")
    @Size(min = 2, max = 255, message = "{CreateUser.LastName.Size}")
    private String lastName;
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
        return "NewUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", details=" + details +
                '}';
    }
}
