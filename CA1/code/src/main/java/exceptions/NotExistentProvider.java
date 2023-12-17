package exceptions;

import static defines.Errors.NOT_EXISTENT_PROVIDER;

public class NotExistentProvider extends Exception {
    public NotExistentProvider() {
        super(NOT_EXISTENT_PROVIDER);
    }
}
