package model;

import exceptions.NotInStock;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Commodity {
    private String id;
    private String name;
    private String providerId;
    private int price;
    private ArrayList<String> categories = new ArrayList<>();
    private float rating;
    private int inStock;
    private String image;

    private Map<String, Integer> userRate = new HashMap<>();
    private float initRate;

    public void updateInStock(int amount) throws NotInStock {
        if ((this.inStock + amount) < 0)
            throw new NotInStock();
        this.inStock += amount;
    }

    public void addRate(String username, int score) {
        userRate.put(username, score);
        this.calcRating();
    }

    private void calcRating() {
        float sum = 0;
        for (Map.Entry<String, Integer> entry : this.userRate.entrySet()) {
            sum += entry.getValue();
        }

        this.rating = ((this.initRate + sum) / (this.userRate.size() + 1));
    }
}
