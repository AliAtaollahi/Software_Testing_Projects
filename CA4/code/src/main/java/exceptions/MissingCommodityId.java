package exceptions;

import static defines.Errors.MISSING_COMMODITY_ID;

public class MissingCommodityId extends Exception {
    public MissingCommodityId() {
        super(MISSING_COMMODITY_ID);
    }
}
