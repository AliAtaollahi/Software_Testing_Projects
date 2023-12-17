package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EngineTest {
    private Engine engine;

    @BeforeEach
    public void init() {
        engine = new Engine();
    }

    public static Order creatFakeOrder(int id, int customer, int price, int quantity) {
        Order order = new Order();
        order.setId(id);
        order.setCustomer(customer);
        order.setPrice(price);
        order.setQuantity(quantity);
        return order;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Test getAverageOrderQuantityByCustomer having orderHistory and customer_id in it")
    public void getAverageOrderQuantityByCustomerHavingOrderHistoryAndCustomerIdTest() {
        int targetCustomer = 1;
        int price1 = 100;
        int price2 = 100;
        int firstQuantity = 10;
        int secondQuantity = 10;
        engine.orderHistory.add(creatFakeOrder(1, targetCustomer, price1, firstQuantity));
        engine.orderHistory.add(creatFakeOrder(2, targetCustomer, price2, secondQuantity));
        engine.orderHistory.add(creatFakeOrder(3, targetCustomer + 1, 1000, 1000));
        int expectedAverage = (price1 + price2) / (secondQuantity + firstQuantity);
        Assertions.assertEquals(expectedAverage, engine.getAverageOrderQuantityByCustomer(targetCustomer));
    }

    @Test
    @DisplayName("Test getAverageOrderQuantityByCustomer without adding order to orderHistory")
    public void getAverageOrderQuantityByCustomerWithEmptyOrderHistoryTest() {
        Assertions.assertEquals(0, engine.getAverageOrderQuantityByCustomer(10));
    }

    @Test
    @DisplayName("Test getAverageOrderQuantityByCustomer having orderHistory and not customer_id in it")
    public void getAverageOrderQuantityByCustomerHavingOrderHistoryAndNoCustomerIdTest() {
        int customer = 1;
        int price = 20;
        int quantity = 20;
        engine.orderHistory.add(creatFakeOrder(1, customer, price, quantity));
        Assertions.assertThrows(ArithmeticException.class, () -> { engine.getAverageOrderQuantityByCustomer(customer + 1); });
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Test getQuantityPatternByPrice having orderHistory and right pattern")
    public void getQuantityPatternByPriceHavingOrderHistoryAndRightPatternTest() {
        int customer = 1;
        int price = 200;
        int startQuantity = 20;
        int stepQuantity = 10;
        engine.orderHistory.add(creatFakeOrder(1, customer, price, startQuantity));
        engine.orderHistory.add(creatFakeOrder(2, customer, price, 1 * stepQuantity + startQuantity));
        engine.orderHistory.add(creatFakeOrder(3, customer + 1, price, 2 * stepQuantity + startQuantity));
        Assertions.assertEquals(stepQuantity,
                engine.getQuantityPatternByPrice(price));
    }

    @Test
    @DisplayName("Test getQuantityPatternByPrice without orderHistory")
    public void getQuantityPatternByPriceWithoutOrderHistoryTest() {
        Assertions.assertEquals(0, engine.getQuantityPatternByPrice(80));
    }

    @Test
    @DisplayName("Test getQuantityPatternByPrice having orderHistory and not right pattern")
    public void getQuantityPatternByPriceHavingOrderHistoryAndNotRightPatternTest() {
        int customer = 1;
        int price = 100;
        int startQuantity = 10;
        int stepQuantity = 5;
        engine.orderHistory.add(creatFakeOrder(1, customer, price, startQuantity));
        engine.orderHistory.add(creatFakeOrder(2, customer, price, 1 * stepQuantity + startQuantity + 60));
        engine.orderHistory.add(creatFakeOrder(3, customer + 1, price, 2 * stepQuantity + startQuantity));
        Assertions.assertEquals(0,
                engine.getQuantityPatternByPrice(price));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Test getCustomerFraudulentQuantity having more than average price")
    public void getCustomerFraudulentQuantityHavingMoreThanAveragePriceTest() {
        int customer = 1;
        int firstQuantity = 20;
        int secondQuantity = 30;
        int averageQuantity = (firstQuantity + secondQuantity) / 2;
        int stepQuantity = 5;
        Order firstOrder = creatFakeOrder(1, customer, 2500, firstQuantity);
        Order secondOrder = creatFakeOrder(2, customer, 2500, secondQuantity);
        Order fakeOrder = creatFakeOrder(3, customer, 2500, averageQuantity + stepQuantity);
        engine.orderHistory.add(firstOrder);
        engine.orderHistory.add(secondOrder);
        Assertions.assertEquals(stepQuantity, engine.getCustomerFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test getCustomerFraudulentQuantity having average price")
    public void getCustomerFraudulentQuantityHavingAveragePriceTest() {
        int customer = 1;
        int firstQuantity = 20;
        int secondQuantity = 30;
        int averageQuantity = (firstQuantity + secondQuantity) / 2;
        Order firstOrder = creatFakeOrder(1, customer, 2500, firstQuantity);
        Order secondOrder = creatFakeOrder(2, customer, 2500, secondQuantity);
        Order fakeOrder = creatFakeOrder(3, customer, 2500, averageQuantity);
        engine.orderHistory.add(firstOrder);
        engine.orderHistory.add(secondOrder);
        Assertions.assertEquals(0, engine.getCustomerFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test getCustomerFraudulentQuantity having less than price")
    public void getCustomerFraudulentQuantityHavingLessThanPriceTest() {
        int customer = 1;
        int firstQuantity = 20;
        int secondQuantity = 30;
        int averageQuantity = (firstQuantity + secondQuantity) / 2;
        int stepQuantity = 5;
        Order firstOrder = creatFakeOrder(1, customer, 2500, firstQuantity);
        Order secondOrder = creatFakeOrder(2, customer, 2500, secondQuantity);
        Order fakeOrder = creatFakeOrder(3, customer, 2500, averageQuantity - stepQuantity);
        engine.orderHistory.add(firstOrder);
        engine.orderHistory.add(secondOrder);
        Assertions.assertEquals(0, engine.getCustomerFraudulentQuantity(fakeOrder));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Test addOrderAndGetFraudulentQuantity having more than average price")
    public void addOrderAndGetFraudulentQuantityHavingMoreThanAveragePriceTest() {
        int customer = 1;
        int firstQuantity = 20;
        int secondQuantity = 30;
        int thirdQuantity = 200;
        int stepQuantity = thirdQuantity - ((firstQuantity + secondQuantity) / 2);
        Order firstOrder = creatFakeOrder(1, customer, 4000, firstQuantity);
        Order secondOrder = creatFakeOrder(2, customer, 4000, secondQuantity);
        Order fakeOrder = creatFakeOrder(3, customer, 4000, thirdQuantity);
        engine.orderHistory.add(firstOrder);
        engine.orderHistory.add(secondOrder);
        Assertions.assertEquals(stepQuantity, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test addOrderAndGetFraudulentQuantity having average price")
    public void addOrderAndGetFraudulentQuantityHavingAveragePriceTest() {
        int customer = 1;
        int price = 100;
        int startQuantity = 10;
        int stepQuantity = 5;
        engine.orderHistory.add(creatFakeOrder(1, customer, price, startQuantity));
        engine.orderHistory.add(creatFakeOrder(2, customer, price, 75));
        engine.orderHistory.add(creatFakeOrder(3, customer + 1, price, startQuantity + 2 * stepQuantity));
        Order fakeOrder = creatFakeOrder(4, 1, price, 1);
        Assertions.assertEquals(0, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test addOrderAndGetFraudulentQuantity having less than price")
    public void addOrderAndGetFraudulentQuantityHavingLessThanPriceTest() {
        int customer = 1;
        int price = 100;
        int startQuantity = 10;
        int stepQuantity = 5;
        engine.orderHistory.add(creatFakeOrder(1, customer, price, startQuantity));
        engine.orderHistory.add(creatFakeOrder(2, customer, price, startQuantity + 1 * stepQuantity));
        engine.orderHistory.add(creatFakeOrder(3, customer + 1, price, startQuantity + 2 * stepQuantity));
        Order fakeOrder = creatFakeOrder(4, 1, price, 1);
        Assertions.assertEquals(stepQuantity, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test addOrderAndGetFraudulentQuantity having orderHistory")
    public void addOrderAndGetFraudulentQuantityHavingOrderHistory() {
        Order fakeOrder = creatFakeOrder(2, 1, 200, 10);
        engine.orderHistory.add(fakeOrder);
        Assertions.assertEquals(0, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test addOrderAndGetFraudulentQuantity having orderHistory and another order")
    public void addOrderAndGetFraudulentQuantityHavingOrderHistoryAndAnotherOrderTest() {
        int customer = 1;
        Order firstOrder = creatFakeOrder(1, customer, 4000, 20);
        Order fakeOrder = creatFakeOrder(2, customer, 4000, 10);
        engine.orderHistory.add(fakeOrder);
        engine.orderHistory.add(firstOrder);

        Assertions.assertEquals(0, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }

    @Test
    @DisplayName("Test getCustomerFraudulentQuantity without orderHistory")
    public void addOrderAndGetFraudulentQuantityWithoutOrderHistoryTest() {
        Order fakeOrder = creatFakeOrder(2, 1, 100, 0);
        Assertions.assertEquals(0, engine.addOrderAndGetFraudulentQuantity(fakeOrder));
    }
}

