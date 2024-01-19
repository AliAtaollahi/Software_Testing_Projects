package exceptions;

import static defines.Errors.COMMODITY_IS_ALREADY_IN_THE_BUY_LIST;

public class AlreadyInBuyList extends Exception {
    public AlreadyInBuyList() {
        super(COMMODITY_IS_ALREADY_IN_THE_BUY_LIST);
    }

}
