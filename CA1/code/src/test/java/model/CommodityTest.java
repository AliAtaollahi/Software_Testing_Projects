package model;

import exceptions.NotInStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CommodityTest {

    @Test
    @DisplayName("Test updateInStock method using valid amounts")
    public void updateInStockTest() throws NotInStock {
        int initialStock = 110;
        int changeStock = -100;
        double precision =  0.001;

        Commodity commodity = createParameterizedInStockCommodity(initialStock);
        commodity.updateInStock(changeStock);
        assertEquals(initialStock + changeStock, commodity.getInStock(), precision);
    }

    public static Commodity createParameterizedInStockCommodity(int inStock) {
        Commodity commodity = createFakeCommodity();
        commodity.setInStock(inStock);
        return commodity;
    }

    public static Commodity createFakeCommodity() {
        Commodity commodity = new Commodity();
        commodity.setId(String.valueOf(1));
        return commodity;
    }

    @Test
    @DisplayName("Test updateInStock method using Invalid amounts")
    public void UpdateInStockInvalidAmountTest() {
        int initialStock = 100;
        int changeStock = -110;

        Commodity commodity = createParameterizedInStockCommodity(initialStock);
        NotInStock exception = assertThrows(NotInStock.class, () -> commodity.updateInStock(changeStock));
        assertEquals("Commodity is not in stock.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "reza_ali, -5",
            "reza_meza, 11",
            "reza_asghar, 0",
    })
    @DisplayName("Test addRate method using invalid score that is out of range of score")
    public void AddRateInvalidScoreTest(String username, int score) {
        Commodity commodity = createFakeCommodity();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> commodity.addRate(username, score));
        assertEquals("Invalid number for score", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "reza, 4, 3, 3.5",
            "asghar, 2, 9, 5.5",
            "masih, 5, 5, 5",
            "nima, 1, 1, 1"
    })
    @DisplayName("Test calcRating method using init rate and new score and expected to return average of those")
    public void CalcRatingOneRateTest(String name, int initialRate, int rate, float expectedRate) throws IllegalArgumentException {
        double precision =  0.001;

        Commodity commodity = createParameterizedInitRateCommodity(initialRate);
        commodity.addRate(name, rate);
        assertEquals(expectedRate, commodity.getRating(), precision);
    }

    public static Commodity createParameterizedInitRateCommodity(int initRate) {
        Commodity commodity = createFakeCommodity();
        commodity.setInitRate(initRate);
        return commodity;
    }

    @Test
    @DisplayName("Test calcRating method using rewritten rate (both rate have similar rater)")
    public void testCalcRatingOneRatingReWrittenWithInitRate() throws IllegalArgumentException {
        int initRate = 1, firstRate = 2, secondRate = 5;

        Commodity commodity = createParameterizedInitRateCommodity(initRate);
        commodity.addRate("reza", firstRate);
        commodity.addRate("reza", secondRate);

        assertEquals((initRate + secondRate) / 2, commodity.getRating(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "a1, 4, a2, 1, a3, 4, 2.25",
            "b1, 2, b2, 3, b3, 5, 2.5",
            "c1, 3, c2, 3, c3, 6, 3",
    })
    @DisplayName("Test calcRating method using init rate and new score and expected to return average of those")
    public void CalcRatingOneRateTest(String name1, int rate1, String name2, int rate2, String name3, int rate3, float expectedRate) throws IllegalArgumentException {
        double precision =  0.001;
        int initialRate = 0;

        Commodity commodity = createParameterizedInitRateCommodity(initialRate);
        commodity.addRate(name1, rate1);
        commodity.addRate(name2, rate2);
        commodity.addRate(name3, rate3);

        assertEquals(expectedRate, commodity.getRating(), precision);
    }

}
