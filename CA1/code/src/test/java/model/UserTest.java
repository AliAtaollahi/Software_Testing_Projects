import exceptions.*;
import model.Commodity;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private Logger logger = Logger.getAnonymousLogger();

    @ParameterizedTest
    @CsvSource({
            "0f, 0f, 0f",
            "100f, 425.5f, 525.5f",
            "10f, 503f, 513f",
    })
    @DisplayName("Test addCredit using valid values")
    public void addCreditTest(float initValue, float increment, float expectedCredit) throws InvalidCreditRange {
        User user = createFakeUserWithCredit(initValue);

        user.addCredit(increment);
        float actualCredit = user.getCredit();

        assertEquals(expectedCredit, actualCredit);
    }

    public static User createFakeUserWithCredit(float credit) {
        User user = createFakeUser();
        user.setCredit(credit);
        return user;
    }

    public static User createFakeUser() {
        return new User("ata", "ali123ata", "ali123ata", "2002-09-21 00:20:10", "Iran, Karaj");
    }

    @ParameterizedTest
    @ValueSource(floats = {-45, -10.5f, -Float.MAX_VALUE})
    @DisplayName("Test addCredit with multiple Invalid credit")
    public void addCreditWithInvalidCreditTest(float increment) {
        User user = createFakeUser();

        InvalidCreditRange exception = assertThrows(InvalidCreditRange.class, () -> user.addCredit(increment));

        assertEquals("Credit value must be a positive float", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "100.47f, 0f, 100.47f",
            "65f, 45.5f, 19.5f",
            "50f, 50f, 0f",
    })
    @DisplayName("Test withdrawCredit using multiple values and decrease amount")
    public void withdrawCreditTest(float initValue, float decrease, float expectedCredit) throws InsufficientCredit, InvalidWithdrawException {
        User user = createFakeUserWithCredit(initValue);

        user.withdrawCredit(decrease);
        float actualCredit = user.getCredit();

        assertEquals(expectedCredit, actualCredit);
    }

    @Test
    @DisplayName("Test withdrawCredit using invalid amount")
    public void withdrawCreditInValidAmountTest() throws InsufficientCredit {
        User user = createFakeUser();

        InvalidWithdrawException exception = assertThrows(InvalidWithdrawException.class, () -> user.withdrawCredit(-1f));

        assertEquals("Withdraw can't be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "100f, 101f",
            "10f, 10.1f",
            "0f, 1000f",
    })
    @DisplayName("Test withdrawCredit using initial values and decrease amounts are bigger than initial")
    public void withdrawCreditOverWithdrawCreditTest(float initValue, float decrease) {
        User user = createFakeUserWithCredit(initValue);

        InsufficientCredit exception = assertThrows(InsufficientCredit.class, () -> user.withdrawCredit(decrease));

        assertEquals("Credit is insufficient.", exception.getMessage());
    }

    @Test
    @DisplayName("Test addBuyItem using commodity that not added before")
    public void addBuyItemTest() throws NotInStock {
        User user = createFakeUser();
        int inStock = 1;
        String randomId = RandomStringUtils.randomAlphanumeric(20);
        Commodity fakeCommodity = createFakeCommodityWithInStock(randomId, inStock);

        logger.log(Level.INFO, "commodity id = " + randomId);

        user.addBuyItem(fakeCommodity);

        Map<String, Integer> userBuyList = user.getBuyList();
        int expetedId = 1;
        assertEquals(expetedId, userBuyList.get(randomId));
    }

    public static Commodity createFakeCommodityWithInStock(String id, int inStock) {
        Commodity fakeCommodity = new Commodity();
        fakeCommodity.setInStock(inStock);
        fakeCommodity.setId(id);
        return fakeCommodity;
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 10, 1, Integer.MAX_VALUE})
    @DisplayName("Test addBuyItem using commodity that exists before")
    public void addBuyItemExistItemTest(int quantity) throws NotInStock {
        int commodity_in_stock = 1;
        String id = RandomStringUtils.randomAlphanumeric(20);
        Commodity fakeCommodity = createFakeCommodityWithInStock(id, commodity_in_stock);
        User user = createFakeUserWithItem(id, quantity);

        logger.log(Level.INFO, "commodity exists before test : quantity = " + quantity + ", commodity id = " + id);

        user.addBuyItem(fakeCommodity);

        assertEquals(quantity + 1, user.getBuyList().get(id));
    }

    public static User createFakeUserWithItem(String id, int quantity) {
        User user = createFakeUser();
        Map<String, Integer> buyList = new HashMap<>();
        buyList.put(id, quantity);
        user.setBuyList(buyList);
        return user;
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 10, 1, Integer.MAX_VALUE})
    @DisplayName("Test addBuyItem using commodity with out of stock")
    public void addBuyItemZeroInStockTest(int quantity) throws NotInStock {
        int inStock = 0;
        String id = RandomStringUtils.randomAlphanumeric(20);
        Commodity commodity = createFakeCommodityWithInStock(id, inStock);
        User user = createFakeUserWithItem(id, quantity);
        logger.log(Level.INFO, "commodity exists before test : quantity = " + quantity + ", commodity id = " + id);

        NotInStock exception = assertThrows(NotInStock.class, () -> user.addBuyItem(commodity));
        assertEquals("Commodity is not in stock.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 10, 1, Integer.MAX_VALUE})
    @DisplayName("Test addPurchasedItem with valid quantity and item not exist before")
    public void addPurchasedItemTest(int quantity) throws InvalidNumQuantityException {
        String id = RandomStringUtils.randomAlphanumeric(20);
        User user = createFakeUser();
        logger.log(Level.INFO, "commodity exists before test : quantity = " + quantity + ", id = " + id);

        user.addPurchasedItem(id, quantity);

        assertEquals(quantity, user.getPurchasedList().get(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1000, -1, 0, Integer.MIN_VALUE})
    @DisplayName("Test addPurchasedItem using invalid quantities")
    public void addPurchasedItemInvalidQuantityTest(int quantity) {
        User user = createFakeUser();

        String id = RandomStringUtils.randomAlphanumeric(20);

        logger.log(Level.INFO, "commodity exists before test : quantity = " + quantity + ", id = " + id);

        InvalidNumQuantityException exception = assertThrows(InvalidNumQuantityException.class, () -> user.addPurchasedItem(id, quantity));
        assertEquals("Invalid quantity", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 700, 800",
            "10, 1, 11",
            "0, 10, 10",
            "12312312, 12, 12312324",
    })
    @DisplayName("Test addPurchasedItem using valid amounts for item exists before")
    public void addPurchasedItemThatItemExistTest(int initQuantity, int increment, int expectedQunatity) throws InvalidNumQuantityException {
        String id = RandomStringUtils.randomAlphanumeric(20);
        User user = createFakeUserWithPurchased(id, initQuantity);
        logger.log(Level.INFO, "id = " + id + ", initQuantity = " + initQuantity + ", increment = " + increment);

        user.addPurchasedItem(id, increment);

        assertEquals(expectedQunatity, user.getPurchasedList().get(id));
    }

    public static User createFakeUserWithPurchased(String id, int quantity) {
        User user = createFakeUser();
        Map<String, Integer> purchasedList = new HashMap<>();
        purchasedList.put(id, quantity);
        user.setPurchasedList(purchasedList);
        return user;
    }

    @Test
    @DisplayName("Test removeItemFromBuyList using an item doesn't exists")
    public void removeItemFromBuyListForNewItemTest() {
        User user = createFakeUser();
        String id = RandomStringUtils.randomAlphanumeric(20);
        Commodity fakeCommodity = createFakeCommodityWithInStock(id, 1);

        logger.log(Level.INFO, "commodity id = " + id);

        CommodityIsNotInBuyList exception = assertThrows(CommodityIsNotInBuyList.class, () -> user.removeItemFromBuyList(fakeCommodity));

        assertEquals("Commodity is not in the buy list.", exception.getMessage());
    }

    @Test
    @DisplayName("Test removeItemFromBuyList using item that has one quantity")
    public void removeItemFromBuyListThatHasOneQuantityTest() throws CommodityIsNotInBuyList {
        String id = RandomStringUtils.randomAlphanumeric(20);
        Commodity fakeCommodity = createFakeCommodityWithInStock(id, 1);
        User user = createFakeUserWithItem(id, 1);

        logger.log(Level.INFO, "commodity exists before test : quantity = " + 1 + ", commodity id = " + id);

        user.removeItemFromBuyList(fakeCommodity);

        assertFalse(user.getBuyList().containsKey(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 100, 10, 2})
    @DisplayName("Test removeItemFromBuyList using item that has more than one quantity")
    public void removeItemFromBuyListExistItemNot1QuantityTest(int quantity) throws CommodityIsNotInBuyList {
        String id = RandomStringUtils.randomAlphanumeric(20);
        Commodity fakeCommodity = createFakeCommodityWithInStock(id, 1);
        User user = createFakeUserWithItem(id, quantity);
        logger.log(Level.INFO, "commodity exists before test : quantity = " + quantity + ", commodity id = " + id);

        user.removeItemFromBuyList(fakeCommodity);

        int expectedQuantity = quantity - 1;
        assertEquals(expectedQuantity, user.getBuyList().get(id));
    }

}
