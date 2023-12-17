package controllers;

import controllers.AuthenticationController;
import exceptions.UsernameAlreadyTaken;
import model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import service.Baloot;
import model.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static defines.Errors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    private String username, password, email, birthDate, address;
    private User user;
    private Map<String, String> input;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Mock
    private Baloot baloot;

    @InjectMocks
    private AuthenticationController authenticationController;


    public static User createFakeUser() {
        String username = "ali";
        String password = "ali123ata";
        String email = "atask@gmail.com";
        String birthDate = "1999-02-01";
        String address = "Karaj, Iran";

        return new User(username, password, email, birthDate, address);
    }

    @BeforeEach
    public void setUp() {
        user = createFakeUser();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        birthDate = user.getBirthDate();
        address = user.getAddress();
        input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        input.put("email", email);
        input.put("birthDate", birthDate);
        input.put("address", address);

        authenticationController = new AuthenticationController();
        authenticationController.setBaloot(baloot);
    }

    @Test
    @DisplayName("Test a user's successful login")
    public void loginTest() throws Exception {
        ResponseEntity<String> response = authenticationController.login(input);

        verify(baloot).login(username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("login successfully!", response.getBody());
    }

    @Test
    @DisplayName("Test login failure due to incorrect password")
    public void loginNotCorrectPasswordTest() throws Exception {
        doThrow(new IncorrectPassword()).when(baloot).login(username, password);

        ResponseEntity<String> response = authenticationController.login(input);

        verify(baloot, times(1)).login(username, password);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(INCORRECT_PASSWORD, response.getBody());
    }

    @Test
    @DisplayName("Test login failure with an unavailable user")
    public void loginUnavailableUserTest() throws NotExistentUser, IncorrectPassword {
        doThrow(new NotExistentUser()).when(baloot).login(username, password);

        ResponseEntity<String> response = authenticationController.login(input);

        verify(baloot, times(1)).login(username, password);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_EXISTENT_USER, response.getBody());
    }

    private void assertEqualsTwoUsers(User firstUser, User secondUser) {
        assertEquals(firstUser.getEmail(), secondUser.getEmail());
        assertEquals(firstUser.getAddress(), secondUser.getAddress());
        assertEquals(firstUser.getUsername(), secondUser.getUsername());
        assertEquals(firstUser.getPassword(), secondUser.getPassword());
        assertEquals(firstUser.getBirthDate(), secondUser.getBirthDate());
    }

    @Test
    @DisplayName("Test successful user signup")
    public void signupTest() throws Exception {

        ResponseEntity<String> response = authenticationController.signup(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("signup successfully!", response.getBody());

        verify(baloot).addUser(userArgumentCaptor.capture());
        verify(baloot, times(1)).addUser(any(User.class));

        User fakeUser = userArgumentCaptor.getValue();
        assertEqualsTwoUsers(user, fakeUser);
    }

    @Test
    @DisplayName("Test signup failure with a repetitive username")
    public void signupRepetitiveUsernameTest() throws Exception {

        doThrow(new UsernameAlreadyTaken()).when(baloot).addUser(any(User.class));

        ResponseEntity<String> response = authenticationController.signup(input);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(USERNAME_ALREADY_TAKEN, response.getBody());

        verify(baloot).addUser(userArgumentCaptor.capture());
        verify(baloot, times(1)).addUser(any(User.class));

        User fakeUser = userArgumentCaptor.getValue();
        assertEqualsTwoUsers(user, fakeUser);
    }

    @Nested
    @DisplayName("Tests for Missing Data Scenarios")
    class MissingDataTester {
        private void makeInputIncomplete(String key) {
            input.remove(key);
        }

        @ParameterizedTest
        @DisplayName("Test login with missing data (specifically password or username)")
        @ValueSource(strings = {"password", "username"})
        public void loginNotProvidedSomeDataTest(String key) throws Exception {
            makeInputIncomplete(key);

            ResponseEntity<String> response = authenticationController.login(input);

            verify(baloot, times(0)).login(any(), any());

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(INVALID_FORMAT_FOR_REQUEST, response.getBody());
        }

        @ParameterizedTest
        @DisplayName("Test signup with missing data (password, username, email, birthDate, or address)")
        @ValueSource(strings = {"password", "username", "birthDate", "email", "address"})
        public void signupNotProvidedSomeDataTest(String key) throws Exception {
            makeInputIncomplete(key);

            ResponseEntity<String> response = authenticationController.signup(input);

            verify(baloot, times(0)).addUser(any());

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(INVALID_FORMAT_FOR_REQUEST, response.getBody());
        }
    }
}
