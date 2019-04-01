package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.UserNameOrPasswordWrongException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Before
    public void createNewUser() {
        final List<UserDetails> detailsList = new ArrayList<>();
        final User user = new User()
                .withUserName("alok2")
                .withActive(true)
                .withPassword("blahblah")
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
        final String expectedPass = "blahblah";
        Optional<User> user = userRepository.findById(userId);
        assertTrue("User should be created", user.isPresent());
        assertNotEquals("User password must be different if it was hashed", expectedPass, user.get().getPassword());
    }

    @Test
    public void validateUserNameAndPassword() {
        final AuthenticatedUser authenticatedUser = userService.authenticateUser("alok2", "blahblah");

        assertEquals("UserId is the same", userId, authenticatedUser.getUserId());
        assertTrue("User has an email", authenticatedUser.getHasEmail());
        assertFalse("User has no phone", authenticatedUser.getHasPhone());
    }

    @Test(expected = UserNameOrPasswordWrongException.class)
    public void validateNotExistingUserNameAndPassword() {
        userService.authenticateUser("alok3", "blahblah");
    }

    @Test
    public void shouldUnlockUserWithId() {
        userService.unlockUser(userId);

        Optional<User> user = userRepository.findById(userId);

        assertTrue("User should be found", user.isPresent());
        assertTrue("User active status should be changed", user.get().getActive());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldUnlockUserWithFalseId() {
        userService.unlockUser("this-is-a-wrong-id-that-does-not-exists");
    }
}