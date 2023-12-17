package exceptions;

import static defines.Errors.NOT_EXISTENT_COMMODITY;

public class NotExistentCommodity extends Exception {
    public NotExistentCommodity() {
        super(NOT_EXISTENT_COMMODITY);
    }
}
