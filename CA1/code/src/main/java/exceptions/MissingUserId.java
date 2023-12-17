package exceptions;

import static defines.Errors.MISSING_USER_ID;

public class MissingUserId extends Exception {
    public MissingUserId() {
        super(MISSING_USER_ID);
    }
}
