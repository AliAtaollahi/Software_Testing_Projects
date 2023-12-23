package utils;

import model.Commodity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyListItem {
    private Commodity commodity;
    private int quantity;

    public BuyListItem(Commodity commodity, int quantity) {
        this.commodity = commodity;
        this.quantity = quantity;
    }
}
