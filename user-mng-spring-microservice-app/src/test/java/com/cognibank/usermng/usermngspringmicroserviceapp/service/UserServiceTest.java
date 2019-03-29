package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl.UserAlreadyExistsException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl.UserServiceImpl;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.Impl.ValidatedUser;
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
public class UserServiceTest {
    String userId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Before
    public void createNewUser() {
        User user = new User();
        List<UserDetails> detailsList = new ArrayList<>();
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
                .withFieldName(UserServiceImpl.EMAIL)
                .withFieldValue("some@email.com"));
        user.withUserName("alok2")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User)
                .withUserDetails(detailsList);

        userId = userService.createNewUser(user);

        assertNotNull("User must have an auto created id", userId);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void shouldNotCreateDuplicateUser() {
        userService.createNewUser(new User()
                .withUserName("alok2")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User));
    }

    @Test
    public void shouldStoreHashedPassword() {
        final String expectedPass = "blahblah";
        Optional<User> user = userRepository.findById(userId);
        assertTrue("User should be created", user.isPresent());
        assertNotEquals("User password must be different if it was hashed", expectedPass, user.get().getPassword());
    }

    @Test
    public void testToValidateUserNameAndPassword() {
        ValidatedUser validatedUser = userService.validateUser("alok2", "blahblah");

        assertEquals("UserId is the same", userId, validatedUser.getUserId());
        assertTrue("User has an email", validatedUser.getHasEmail());
        assertFalse("User has no phone", validatedUser.getHasPhone());
    }
}