package exceptions;

import static defines.Errors.COMMODITY_IS_NOT_IN_STOCK;

public class NotInStock extends Exception {
    public NotInStock() {
        super(COMMODITY_IS_NOT_IN_STOCK);
    }
}
