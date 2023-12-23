package defines;

public class Errors {
    public static final String COMMODITY_IS_ALREADY_IN_THE_BUY_LIST = "Commodity is already in the buy list.";
    public static final String COMMODITY_IS_NOT_IN_THE_BUY_LIST = "Commodity is not in the buy list.";
    public static final String COMMODITY_IS_NOT_IN_STOCK = "Commodity is not in stock.";
    public static final String MISSING_USER_ID = "User ID cannot be null.";
    public static final String MISSING_COMMODITY_ID = "Commodity ID cannot be null.";
    public static final String MISSING_CREDIT_VALUE = "Credit value cannot be null.";
    public static final String MISSING_COMMENT_ID = "Comment ID cannot be null.";
    public static final String MISSING_VOTE_VALUE = "Vote value cannot be null.";
    public static final String MISSING_START_OR_END_PRICE = "Start or end price values cannot be null.";
    public static final String MISSING_CATEGORY = "Category cannot be null.";
    public static final String INVALID_RATE_FORMAT = "Rate should be an integer.";
    public static final String INVALID_CREDIT_FORMAT = "Credit should be a float.";
    public static final String INVALID_VOTE_FORMAT = "Invalid vote: vote should be either 1, 0, or -1.";
    public static final String INVALID_RATE_RANGE = "Rate value must be an integer between 1 and 10";
    public static final String INVALID_CREDIT_RANGE = "Credit value must be a positive float";
    public static final String INVALID_PRICE_RANGE = "Invalid Price Range.";
    public static final String INSUFFICIENT_CREDIT = "Credit is insufficient.";
    public static final String NOT_EXISTENT_COMMODITY = "Commodity does not exist.";
    public static final String NOT_EXISTENT_COMMENT = "Comment does not exist.";
    public static final String NOT_EXISTENT_PROVIDER = "Provider does not exist.";
    public static final String NOT_EXISTENT_USER = "User does not exist.";
    public static final String INCORRECT_PASSWORD = "Incorrect password.";
    public static final String USERNAME_ALREADY_TAKEN = "The username is already taken.";
}
