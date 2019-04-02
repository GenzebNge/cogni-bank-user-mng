package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;

import java.util.stream.Collectors;

/**
 *  Class Used to create Authenticated User
 */
public class AuthenticatedUser {
    private String userId;
    private String phone;
    private boolean hasPhone;
    private String email;
    private boolean hasEmail;

    /**
     * Constructor to create an Authenticated User
     * @param user user needs to be authenticated
     * @see UserDetails#getFieldName()
     * @see UserDetails#getFieldValue()
     * @see User#getDetails()
     * @see UserService#EMAIL
     * @see UserService#MOBILE_PHONE
     */
    public AuthenticatedUser(User user) {
        setUserId(user.getId());
        user.getDetails().stream().
                collect(Collectors.toMap(UserDetails::getFieldName, UserDetails::getFieldValue))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals(UserService.EMAIL) || entry.getKey().equals(UserService.MOBILE_PHONE))
                .forEach(e -> {
                    switch (e.getKey()) {
                        case UserService.EMAIL:
                            setEmail(e.getValue());
                            setHasEmail();
                            break;
                        case UserService.MOBILE_PHONE:
                            setPhone(e.getValue());
                            setHasPhone();
                            break;
                    }
                });
    }

    /**
     * Gets if User has a phone or not
     * @return <code> boolean </code> stating if the user has a phone
     * @see #hasPhone
     */
    public boolean getHasPhone() {
        return hasPhone;
    }

    /**
     * Sets if User has a phone or not
     * @see #hasPhone
     */
    private void setHasPhone() {
        this.hasPhone = true;
    }

    /**
     *  Gets if User has an email or not
     * @return <code> boolean </code> stating if the user has an email
     * @see #hasEmail
     */
    public boolean getHasEmail() {
        return hasEmail;
    }

    /**
     * Sets if User has an email or not
     * @see #hasEmail
     */
    private void setHasEmail() {
        this.hasEmail = true;
    }

    /**
     * Gets the UserId of the User
     * @return <code> String </code> UserId of the User
     * @see #userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the UserId of the User
     * @param userId UserId of the User
     * @see #userId
     */
    private void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the PhoneNumber of the User
     * @return <code> String </code> phone Number of the User
     * @see #phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the PhoneNumber of the User
     * @param phone Phone Number of the User
     * @see #phone
     */
    private void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the Email of the User
     * @return <code> String </code> Email of the User
     * @see #email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Email of the User
     * @param email Email of the User
     * @see #email
     */
    private void setEmail(String email) {
        this.email = email;
    }
}
