package exceptions;

import static defines.Errors.INVALID_CREDIT_RANGE;

public class InvalidCreditRange extends Exception {
    public InvalidCreditRange() {
        super(INVALID_CREDIT_RANGE);
    }
}
