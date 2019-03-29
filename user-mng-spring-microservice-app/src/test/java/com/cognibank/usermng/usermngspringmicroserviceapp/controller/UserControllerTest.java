package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import formdata.UserCredentials;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {
//
//    @Rule public MockitoRule rule = MockitoJUnit.rule();
//
//    @Mock
//    private UserRepository userRepository;
//
//     @InjectMocks @Spy
//    private UserService userService;
//
//    @InjectMocks
//    UserController userController;

    @Autowired
    private MockMvc mvc;

//    @Before
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//        userService = new UserService(userRepository);
//        userController = new UserController(userService);
//    }

    @Test
    public void getAllEmployeesAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/version")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(UserController.VERSION));
    }


//
//    @Test
//    public void checkUserCredentials() throws Exception {
//        User user = new User();
//        List<UserDetails> detailsList = new ArrayList<>();
//        detailsList.add(new UserDetails()
//                .withId(1l)
//                .withUser(user)
//                .withFieldName("FirstName")
//                .withFieldValue("Foo"));
//        detailsList.add(new UserDetails()
//                .withId(2l)
//                .withUser(user)
//                .withFieldName("LastName")
//                .withFieldValue("Bar"));
//        detailsList.add(new UserDetails()
//                .withId(3l)
//                .withUser(user)
//                .withFieldName(UserService.EMAIL)
//                .withFieldValue("some@email.com"));
//        user.withUserName("alok")
//                .withId("000afd42-8cfe-44f5-bc58-b52b114c5b70")
//                .withActive(true)
//                .withPassword("blahblah")
//                .withType(UserType.User)
//                .withUserDetails(detailsList);
//
//
//        UserCredentials userCredentials = new UserCredentials();
//        userCredentials.userName = "alok";
//        userCredentials.password = "blahblah";
//
//        Mockito.when(userRepository.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
//
//      UserService.ValidatedUser validatedUser= userController.checkUserNamePassword(userCredentials);
//       // Mockito.when(userService.validateUser(Mockito.matches("alok"), Mockito.matches("blahblah"))).thenReturn();
//
//        assertNotNull(validatedUser);
////        mvc.perform(MockMvcRequestBuilders
////                .post("/checkUserNamePassword")
////                .content("{\"userName\":\"alok\", \"password\":\"blahblah\"}")
////                .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.userId").value("000afd42-8cfe-44f5-bc58-b52b114c5b70"));
//    }
//



}