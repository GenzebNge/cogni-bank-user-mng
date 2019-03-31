package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.impl.UserControllerImpl;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.UserNameOrPasswordWrongException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        MockitoAnnotations.initMocks(this);
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void shouldReturnVersion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/version")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(UserController.VERSION));
    }

    @Test
    public void shouldReceiveOkStatusWithUserIdAndCorrectFlagsForEmailAndPhoneWhenCorrectLoginRequestComes() throws Exception {
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
                .withFieldName(UserService.EMAIL)
                .withFieldValue("some@email.com"));
        user.withUserName("alok")
                .withId("000afd42-8cfe-44f5-bc58-b52b114c5b70")
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User)
                .withUserDetails(detailsList);

        Mockito.when(userService.authenticateUser(Mockito.eq("alok"), Mockito.anyString()))
                .thenReturn(new AuthenticatedUser(user));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/checkUserNamePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"alok\", \"password\":\"blahblah\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.hasPhone").value(false))
                .andExpect(jsonPath("$.hasEmail").value(true));
    }

    @Test
    public void shouldReceiveForbiddenStatusWhenWrongLoginRequestComes() throws Exception {
        Mockito.when(userService.authenticateUser(Mockito.eq("alok2"), Mockito.anyString()))
                .thenThrow(new UserNameOrPasswordWrongException());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/checkUserNamePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"alok2\", \"password\":\"blahblah\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReceiveBadRequestWhenInvalidLoginRequestComes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/checkUserNamePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"alok2\", \"password\":\"blahbla \"}") // min 8 char password (trimmed)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReceiveBadRequestWhenInvalidCreateUserRequestComes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"45dgg\", \"password\":\"12345678\", \"email\":\"foo\", \"firstName\":\"Foo\", \"lastName\":\"B\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReceiveOkStatusWithUserIdCreateUser() throws Exception {
        Mockito.when(userService.createNewUser(Mockito.any(User.class)))
                .thenReturn("000afd42-8cfe-44f5-bc58-b52b114c5b70");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"some.new_user123\", \"password\":\"12QWas*-_+\", \"email\":\"foo@bar.com\", \"firstName\":\"Foo\", \"lastName\":\"Bar\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }
}