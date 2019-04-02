package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;

import java.util.Map;

/**
 * This interface defines service methods for the User
 */
public interface UserService {

    String FIRST_NAME = "FirstName";
    String LAST_NAME = "LastName";
    String EMAIL = "Email";
    String MOBILE_PHONE = "MobilePhone";
    // String EMAIL_VALIDATED = "Email-isValidated";
    // String MOBILE_PHONE_VALIDATED = "MobilePhone-isValidated";

    /**
     *  Creates a new User in the database
     *
     * @param user the user object with all the details
     * @return a <code> String </code> which is a UsedID after the User is created
     */
    String createNewUser(final User user);

    /**
     * Authenticates the User
     *
     * @param userName the userName provided by the User when logging in
     * @param password the password provided by the User when logging in
     * @return AuthenticatedUser {@link AuthenticatedUser#AuthenticatedUser(User)} specifying that the User is Authenticated
     */
    AuthenticatedUser authenticateUser(final String userName, final String password);

    /**
     * Unlocks the User
     * @param id Id of the User
     * @return <code> boolean </code> stating that the User is unlocked
     */
    boolean unlockUser(String id);

    /**
     * Updates the User with given details
     * @param id Id of the User
     * @param details Details that the User wants to Update
     * @return <code> boolean </code>  stating that the User details are Updated
     * */
    boolean updateUser(String id, Map<String, String> details);
}
