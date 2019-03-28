package com.cognibank.usermng.usermngspringmicroserviceapp.repository;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void createNewUser() {
        User newUser = new User();
        List<UserDetails> detailsList = new ArrayList<>();
        detailsList.add(new UserDetails()
            .withUser(newUser)
            .withFieldName("FirstName")
            .withFieldValue("Jan"));
        detailsList.add(new UserDetails()
                .withUser(newUser)
                .withFieldName("LastName")
                .withFieldValue("Yapan"));
        newUser.withType(UserType.User)
                .withUserName("canyapan")
                .withPassword("test")
                .withUserDetails(detailsList)
                .withActive(true);

        newUser = userRepository.save(newUser);
        assertNotNull(newUser.getId());
    }
}