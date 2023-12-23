package exceptions;

import static defines.Errors.USERNAME_ALREADY_TAKEN;

public class UsernameAlreadyTaken extends Exception {
    public UsernameAlreadyTaken() {
        super(USERNAME_ALREADY_TAKEN);
    }
}