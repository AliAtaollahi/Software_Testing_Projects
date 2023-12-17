package exceptions;

import static defines.Errors.INSUFFICIENT_CREDIT;

public class InsufficientCredit extends Exception {
    public InsufficientCredit() {
        super(INSUFFICIENT_CREDIT);
    }
}
