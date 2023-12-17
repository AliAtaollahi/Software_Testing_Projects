package domain;

import org.junit.jupiter.api.*;

public class OrderTest {
    private Order order;

    private int defaultId;

    @BeforeEach
    public void setup() {
        order = new Order();
        defaultId = 1;
        order.setId(defaultId);
    }

    @Test
    public void GetterSetterTest() {
        int id = 10;
        int customer = 20;
        int price = 300;
        int quantity = 9;
        order.setId(id);
        order.setCustomer(customer);
        order.setPrice(price);
        order.setQuantity(quantity);
        Assertions.assertEquals(id, order.getId());
        Assertions.assertEquals(customer, order.getCustomer());
        Assertions.assertEquals(price, order.getPrice());
        Assertions.assertEquals(quantity, order.getQuantity());
    }

    @Test
    public void EqualOrderIdTest() {
        Order newOrder = new Order();
        newOrder.setId(defaultId);
        Assertions.assertEquals(order, newOrder);
    }

    @Test
    public void DifferentOrderIdTest() {
        Order newOrder = new Order();
        newOrder.setId(defaultId + 1);
        Assertions.assertNotEquals(order, newOrder);
    }

    @Test
    public void NotEqualNotOrderObjectTest() {
        Object fakeObject = new Object();
        Assertions.assertNotEquals(order, fakeObject);
    }
}
