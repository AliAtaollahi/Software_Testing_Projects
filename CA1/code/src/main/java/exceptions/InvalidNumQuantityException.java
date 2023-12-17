package exceptions;

import static defines.Errors.INVALID_CREDIT_RANGE;
import static defines.Errors.INVALID_NUM_QUANTITY;

public class InvalidNumQuantityException extends Exception {
    public InvalidNumQuantityException() {
        super(INVALID_NUM_QUANTITY);
    }
}
