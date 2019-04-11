package com.cognibank.usermng.usermngspringmicroserviceapp.controller;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.impl.UserControllerImpl;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserType;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserDetailsUpdateException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserNameOrPasswordWrongException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.UserNotFoundException;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.impl.AuthenticatedUser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.core.env.Environment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserControllerTest {

//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    public final String USER_ID = "000afd42-8cfe-44f5-bc58-b52b114c5b70";

//    @Autowired
//    private Environment env;
//    String keyValue = env.getProperty("spring.data.rest.basePath");
//
    //@Value("${spring.data.rest.basePath}")
    String basePath = "/users/management";

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
                .get(basePath.concat("/version"))
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
                .withId(USER_ID)
                .withActive(true)
                .withPassword("blahblah")
                .withType(UserType.User)
                .withUserDetails(detailsList);

        Mockito.when(userService.authenticateUser(Mockito.eq("alok"), Mockito.anyString()))
                .thenReturn(new AuthenticatedUser(user));

        mockMvc.perform(MockMvcRequestBuilders
                .post(basePath.concat("/checkUserNamePassword"))
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
    public void shouldReceiveUnauthorizedStatusWhenWrongLoginRequestComes() throws Exception {
        Mockito.when(userService.authenticateUser(Mockito.eq("alok2"), Mockito.anyString()))
                .thenThrow(new UserNameOrPasswordWrongException());

        mockMvc.perform(MockMvcRequestBuilders
                .post(basePath.concat("/checkUserNamePassword"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"alok2\", \"password\":\"blahblah\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReceiveBadRequestWhenInvalidLoginRequestComes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(basePath.concat("/checkUserNamePassword"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"alok2\", \"password\":\"blahbla \"}") // min 8 char password (trimmed)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReceiveBadRequestWhenInvalidCreateUserRequestComes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(basePath.concat("/createUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"45dgg\", \"password\":\"12345678\", \"email\":\"foo@\", \"firstName\":\"Foo\", \"lastName\":\"B\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReceiveOkStatusWithUserIdCreateUser() throws Exception {
        Mockito.when(userService.createNewUser(Mockito.any(User.class)))
                .thenReturn(USER_ID);

        mockMvc.perform(MockMvcRequestBuilders
                .post(basePath.concat("/createUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"some.new_user123\", \"password\":\"12QWas*-_+\", \"email\":\"foo@bar.com\", \"firstName\":\"Foo\", \"lastName\":\"Bar\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    public void shouldUnlockUserWithUserId() throws Exception {
        Mockito.when(userService.unlockUser(Mockito.anyString()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/unlockUser/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundStatusUserWithNotExistingUserId() throws Exception {
        Mockito.when(userService.unlockUser(Mockito.anyString()))
                .thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/unlockUser/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundStatusWhenUpdatingNotExistingUser() throws Exception {
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.anyMap()))
                .thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/updateUser/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"Email\":\"foo@bar.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateUserDetails() throws Exception {
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/updateUser/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"Email\":\"foo@bar.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updated").isBoolean());
    }

    @Test
    public void shouldNotUpdateFirstNameOrLastName() throws Exception {
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.anyMap()))
                .thenThrow(UserDetailsUpdateException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/updateUser/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"FirstName\":\"NewFirstName\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldChangePassword() throws Exception {
        Mockito.when(userService.changePassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .put(basePath.concat("/changePassword/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newPassword\":\"1234rewq\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.changed").isBoolean());
    }

    @Test
    public void shouldReturnUserDetails() throws Exception {
        String email = "foo@bar.com";
        String firstName = "Genzeb";
        String lastName = "Nge";
        Mockito.when(userService.getUserDetails(Mockito.anyString()))
                .thenReturn(new HashMap<String, String>() {{
                    put(UserService.EMAIL, email);
                    put(UserService.FIRST_NAME, firstName);
                    put(UserService.LAST_NAME, lastName);
                }});

        mockMvc.perform(MockMvcRequestBuilders
                .get(basePath.concat("/getUserDetails/" + USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Email").value(email))
                .andExpect(jsonPath("$.FirstName").value(firstName))
                .andExpect(jsonPath("$.LastName").value(lastName));
    }

    @Test
    public void shouldReturnUserIdProvidedUserNameIsGiven() throws Exception {
        Mockito.when(userService.getUserId(Mockito.anyString())).thenReturn(USER_ID);

        // Calling the mockmvc request.
        mockMvc.perform(MockMvcRequestBuilders.get(basePath.concat("/retrieveUserId/alok"))
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }

}