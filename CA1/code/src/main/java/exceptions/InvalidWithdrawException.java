package exceptions;

import static defines.Errors.INVALID_WITHDRAW_AMOUNT;

public class InvalidWithdrawException extends Exception {
    public InvalidWithdrawException() {
        super(INVALID_WITHDRAW_AMOUNT);
    }
}
