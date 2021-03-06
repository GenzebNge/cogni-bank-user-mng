package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserDetailsUpdateException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserNameOrPasswordWrongException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserNotFoundException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private String userId;

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String USER_NAME = "alok2";
    public static final String PASSWORD = "blahblah";

    @Before
    public void createNewUser() {
        final List<UserDetails> detailsList = new ArrayList<>();
        final User user = new User()
                .withUserName(USER_NAME)
                .withActive(true)
                .withPassword(PASSWORD)
                .withType(UserType.User)
                .withUserDetails(detailsList);
        detailsList.add(new UserDetails()
                .withUser(user)
                .withFieldName("FirstName")
                .withFieldValue("Foo"));
        detailsList.add(new UserDetails()
                .withUser(user)
                .withFieldName("LastName")
                .withFieldValue("Bar"));
        detailsList.add(new UserDetails()
                .withUser(user)
                .withFieldName(UserService.EMAIL)
                .withFieldValue("some@email.com"));

        userId = userService.createNewUser(user);

        assertNotNull("User must have an auto created id", userId);
    }

    @Test
    public void shouldStoreHashedPassword() {
        final String expectedPass = PASSWORD;
        Optional<User> user = userRepository.findById(userId);
        assertTrue("User should be created", user.isPresent());
        assertNotEquals("User password must be different if it was hashed", expectedPass, user.get().getPassword());
    }

    @Test
    public void validateUserNameAndPassword() {
        final AuthenticatedUser authenticatedUser = userService.authenticateUser(USER_NAME, PASSWORD);

        assertEquals("UserId is the same", userId, authenticatedUser.getUserId());
        assertTrue("User has an email", authenticatedUser.getHasEmail());
        assertFalse("User has no phone", authenticatedUser.getHasPhone());
    }

    @Test(expected = UserNameOrPasswordWrongException.class)
    public void validateNotExistingUserNameAndPassword() {
        userService.authenticateUser("alok3", PASSWORD);
    }

    @Test
    public void shouldUnlockUserWithId() {
        Optional<User> user = userRepository.findById(userId);
        assertTrue("User should be found", user.isPresent());
        assertTrue("User should be active", user.get().getActive());

        userService.lockUser(userId);

        user = userRepository.findById(userId);
        assertTrue("User should be found", user.isPresent());
        assertFalse("User should be passive", user.get().getActive());

        userService.unlockUser(userId);

        user = userRepository.findById(userId);
        assertTrue("User should be found", user.isPresent());
        assertTrue("User should be active", user.get().getActive());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldUnlockUserWithFalseId() {
        userService.unlockUser("this-is-a-wrong-id-that-does-not-exists");
    }

    @Test
    @Transactional
    public void shouldUpdateUserDetailsAndInsertNewDetails() {
        final String newEmail = "new@email.com";
        final String newPhone = "740-789-9876";


        Map<String, String> details = new HashMap<String, String>() {{
            put("Email", newEmail);
            put("Phone", newPhone);
        }};

        assertTrue("Should return true", userService.updateUser(userId, details));

        Optional<User> user = userRepository.findById(userId);

        assertTrue("User not found", user.isPresent());

        List<UserDetails> detailsList = user.get().getDetails();
        assertNotNull("UserDetails not found", detailsList);

        assertEquals("Field did not updated", 1,
                detailsList.stream()
                        .filter(d -> d.getFieldName().equals("Email") && d.getFieldValue().equals(newEmail))
                        .count());

        assertEquals("Field did not inserted", 1,
                detailsList.stream()
                        .filter(d -> d.getFieldName().equals("Phone") && d.getFieldValue().equals(newPhone))
                        .count());
    }

    @Test(expected = UserDetailsUpdateException.class)
    @Transactional
    public void shouldNotUpdateFirstName() {
        final String firstName = "FooBar";
        final String newPhone = "740-789-9876";

        Map<String, String> details = new HashMap<String, String>() {{
            put(UserService.FIRST_NAME, firstName);
            put("Phone", newPhone);
        }};

        userService.updateUser(userId, details);
    }

    @Test(expected = UserDetailsUpdateException.class)
    @Transactional
    public void shouldNotUpdateLastName() {
        final String lastName = "FooBar";
        final String newPhone = "740-789-9876";

        Map<String, String> details = new HashMap<String, String>() {{
            put(UserService.LAST_NAME, lastName);
            put("Phone", newPhone);
        }};

        userService.updateUser(userId, details);
    }

    @Test
    public void shouldChangePassword() {
        Optional<User> user = userRepository.findById(userId);
        assertTrue("User not found", user.isPresent());

        String hashedOldPass = user.get().getPassword();

        final String expectedPass = "foobar12";
        boolean changed = userService.changePassword(userId, expectedPass);
        assertTrue("Expected response true", changed);

        user = userRepository.findById(userId);

        assertTrue("User not found", user.isPresent());
        assertNotEquals("User password must be different if it was hashed", expectedPass, user.get().getPassword());
        assertNotEquals("Hashed password should be changed", hashedOldPass, user.get().getPassword());
    }

    @Test
    @Transactional
    public void shouldReturnUserDetailsForAUserWithItsID() {
        Map<String, String> userDetails = userService.getUserDetails(userId);

        Optional<User> user = userRepository.findById(userId);

        assertTrue("User not found", user.isPresent());

        List<UserDetails> detailsList = user.get().getDetails();
        assertNotNull("UserDetails not found", detailsList);

        assertTrue("UserDetails must match with the database",
                detailsList.stream()
                        .allMatch(d -> userDetails.get(d.getFieldName()).equals(d.getFieldValue())));
    }

    @Test
    public void shouldReturnUserIdForAUserWithItsUserName() {
        final String userIdFromService = userService.getUserId(USER_NAME);

        final Optional<User> user = userRepository.findByUserName(USER_NAME);

        assertTrue("User should be present in the database", user.isPresent());

        assertTrue("Should give the same user id", userIdFromService.equalsIgnoreCase(user.get().getId()));

        assertTrue("Should be equal to the user id created in the before each method.",
                userId.equalsIgnoreCase(userIdFromService));
    }

    @Test
    public void shouldReturnUserNameForGivenEmail(){
    logger.info("username of the user {}, when given emailid is  {}",userService.getUserName("some@email.com"), "some@email.com" );
        assertEquals("verifying the user id returned","UserName : alok2", userService.getUserName("some@email.com"));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowAnExceptionWhenGivenEmailThatDoesNotExist(){
        userService.getUserName("pragathi@email.com");
    }

}