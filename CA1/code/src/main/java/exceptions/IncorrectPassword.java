package exceptions;

import static defines.Errors.INCORRECT_PASSWORD;

public class IncorrectPassword extends Exception {
    public IncorrectPassword() {
        super(INCORRECT_PASSWORD);
    }
}

