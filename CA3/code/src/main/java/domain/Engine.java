package domain;

import java.util.ArrayList;

public class Engine {

    ArrayList<Order> orderHistory;

    public Engine() {
        orderHistory = new ArrayList<>();
    }

    int getAverageOrderQuantityByCustomer(int customer) {
        var sum = 0;
        var count = 0;

        for (Order oldOrder : orderHistory) {
            if (oldOrder.customer == customer) {
                sum += oldOrder.quantity;
                count++;
            }
        }

        if (orderHistory.size() == 0) {
            return 0;
        }

        return sum / count;
    }

    int getQuantityPatternByPrice(int price) {
        if (orderHistory.size() == 0) {
            return 0;
        }

        var diff = 0;
        var previous = orderHistory.get(0);

        for (Order currentOrder : orderHistory) {
            if (currentOrder.id == previous.id) {
                continue;
            }

            if (currentOrder.price != price) {
                continue;
            }

            if (diff == 0) {
                diff = currentOrder.quantity - previous.quantity;
                previous = currentOrder;
            } else if (diff != currentOrder.quantity - previous.quantity) {
                return 0;
            }
        }

        return diff;
    }

    int getCustomerFraudulentQuantity(Order order) {

        var averageOrderQuantity = getAverageOrderQuantityByCustomer(order.customer);

        if (order.quantity > averageOrderQuantity) {
            return order.quantity - averageOrderQuantity;
        }

        return 0;
    }

    public int addOrderAndGetFraudulentQuantity(Order order) {
        if (orderHistory.contains(order)) {
            return 0;
        }

        var quantity = getCustomerFraudulentQuantity(order);
        if (quantity == 0) {
            quantity = getQuantityPatternByPrice(order.price);
        }

        orderHistory.add(order);
        return quantity;
    }
}
