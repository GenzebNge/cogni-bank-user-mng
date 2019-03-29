package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
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
        user.withUserName("alok")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User)
                .withUserDetails(detailsList);
        String userId = userService.createNewUser(user);

        assertNotNull("User must have an auto created id", userId);
    }

    @Test(expected = UserService.UserAlreadyExistsException.class)
    public void shouldNotCreateDuplicateUser() {
        User user = new User();
        user.withUserName("alok")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User);
        User user1 = new User();
        user1.withUserName("alok")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User);
        userService.createNewUser(user);
        userService.createNewUser(user1);
    }

    @Test
    public void shouldStoreHashedPassword() {
        final String pass = "blahblah";
        String userId =
                userService.createNewUser(new User()
                        .withUserName("alok")
                        .withActive(true)
                        .withPassword(pass)
                        .withType(UserType.User));

        Optional<User> user = userRepository.findById(userId);
        assertTrue("User should be created", user.isPresent());
        assertNotEquals("User password must be different if it was hashed", pass, user.get().getPassword());
    }
}