package controllers;

import exceptions.InvalidFormatForRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import service.Baloot;
import model.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import exceptions.UsernameAlreadyTaken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {
    private Baloot baloot = Baloot.getInstance();

    public void setBaloot(Baloot baloot) {
        this.baloot = baloot;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> input) {
        if(!input.containsKey("username") || !input.containsKey("password"))
            return new ResponseEntity<>((new InvalidFormatForRequest()).getMessage(), HttpStatus.BAD_REQUEST);

        try {
            String username = input.get("username");
            String password = input.get("password");
            baloot.login(username, password);
            return new ResponseEntity<>("login successfully!", HttpStatus.OK);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IncorrectPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> input) {
        if (!input.containsKey("address")
                || !input.containsKey("birthDate")
                || !input.containsKey("email")
                || !input.containsKey("username")
                || !input.containsKey("password"))
            return new ResponseEntity<>((new InvalidFormatForRequest()).getMessage(), HttpStatus.BAD_REQUEST);

        String address = input.get("address");
        String birthDate = input.get("birthDate");
        String email = input.get("email");
        String username = input.get("username");
        String password = input.get("password");

        User newUser = new User(username, password, email, birthDate, address);
        try {
            baloot.addUser(newUser);
            return new ResponseEntity<>("signup successfully!", HttpStatus.OK);
        } catch (UsernameAlreadyTaken e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

