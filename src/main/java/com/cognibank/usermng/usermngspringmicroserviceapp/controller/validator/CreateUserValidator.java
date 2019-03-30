package com.cognibank.usermng.usermngspringmicroserviceapp.controller.validator;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.model.NewUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class CreateUserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public CreateUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NewUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        NewUser user = (NewUser) o;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "AppUserForm.NotEmpty.email");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailConfirm", "AppUserForm.NotEmpty.emailConfirm");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "AppUserForm.NotEmpty.firstName");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "AppUserForm.NotEmpty.lastName");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "AppUserForm.NotEmpty.password");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "AppUserForm.NotEmpty.passwordConfirm");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telNumber", "AppUserForm.NotEmpty.telNumber");
//
//        if (!user.getEmail().equals(user.getEmailConfirm())) {
//            errors.rejectValue("emailConfirm", "AppUserForm.Diff.emailConfirm");
//        }
//
//        if (!user.getPassword().equals(user.getPasswordConfirm())) {
//            errors.rejectValue("passwordConfirm", "AppUserForm.Diff.passwordConfirm");
//        }
//
//        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", user.getEmail())) {
//            errors.rejectValue("email", "AppUserForm.Invalid.email");
//        }
//
//        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "AppUserForm.Size.password");
//        }
//
//        if (!Pattern.matches("^\\d{10}$", user.getTelNumber())) {
//            errors.rejectValue("telNumber", "AppUserForm.Invalid.telNumber");
//        }
//
//        if (userService.findByEmail(user.getEmail()) != null) {
//            errors.rejectValue("email", "AppUserForm.Duplicate.email");
//        }
    }

}
