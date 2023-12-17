package model;

import exceptions.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private float credit;

    private Map<Integer, Integer> commoditiesRates = new HashMap<>();
    private Map<String, Integer> buyList = new HashMap<>();
    private Map<String, Integer> purchasedList = new HashMap<>();

    public User(String username, String password, String email, String birthDate, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    public void addCredit(float amount) throws InvalidCreditRange {
        if (amount < 0)
            throw new InvalidCreditRange();

        this.credit += amount;
    }

    public void withdrawCredit(float amount) throws InsufficientCredit, InvalidWithdrawException {
        checkAmountForWith(amount);
        this.credit -= amount;
    }

    private void checkAmountForWith(float amount) throws InsufficientCredit, InvalidWithdrawException {
        if (amount > this.credit)
            throw new InsufficientCredit();

        if (amount < 0)
            throw new InvalidWithdrawException();
    }

    public void addBuyItem(Commodity commodity) throws NotInStock {
        String id = commodity.getId();
        checkStuckNum(commodity);
        if (this.buyList.containsKey(id)) {
            int existingQuantity = this.buyList.get(id);
            this.buyList.put(id, existingQuantity + 1);
        } else
            this.buyList.put(id, 1);
    }

    private void checkStuckNum(Commodity commodity) throws NotInStock {
        if (commodity.getInStock() == 0)
            throw new NotInStock();

    }

    public void addPurchasedItem(String id, int quantity) throws InvalidNumQuantityException {
        checkQuantity(quantity);
        if (this.purchasedList.containsKey(id)) {
            int existingQuantity = this.purchasedList.get(id);
            this.purchasedList.put(id, existingQuantity + quantity);
        } else
            this.purchasedList.put(id, quantity);
    }

    private void checkQuantity(int quantity) throws InvalidNumQuantityException {
        if (quantity <= 0)
            throw new InvalidNumQuantityException();
    }

    public void removeItemFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        String id = commodity.getId();
        if (this.buyList.containsKey(id)) {
            int existingQuantity = this.buyList.get(id);
            if (existingQuantity == 1)
                this.buyList.remove(commodity.getId());
            else
                this.buyList.put(id, existingQuantity - 1);
        } else
            throw new CommodityIsNotInBuyList();
    }

}
