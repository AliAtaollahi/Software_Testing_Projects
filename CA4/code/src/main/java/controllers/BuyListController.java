package controllers;

import exceptions.*;
import model.Commodity;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.Baloot;
import utils.BuyListItem;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class BuyListController {

    private Baloot baloot = Baloot.getInstance();

    public void setBaloot(Baloot baloot) {
        this.baloot = baloot;
    }
    @PostMapping(value = "/buy-list")
    public ResponseEntity<ArrayList<BuyListItem>> getBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> buyListItems = new ArrayList<>();

        try {
            Map<String, Integer> buyList = baloot.getUserBuyList(username);
            for (Map.Entry<String, Integer> entry : buyList.entrySet()) {
                Commodity commodity = baloot.getCommodityById(entry.getKey());
                int quantity = entry.getValue();

                BuyListItem buyListItem = new BuyListItem(commodity, quantity);
                buyListItems.add(buyListItem);
            }
            return new ResponseEntity<>(buyListItems, HttpStatus.OK);

        } catch (NotExistentUser | NotExistentCommodity ignored) {
            return new ResponseEntity<>(buyListItems, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/purchased-list")
    public ResponseEntity<ArrayList<BuyListItem>> getPurchasedList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> purchasedListItems = new ArrayList<>();

        try {
            Map<String, Integer> purchasedList = baloot.getUserPurchasedList(username);
            for (Map.Entry<String, Integer> entry : purchasedList.entrySet()) {
                Commodity commodity = baloot.getCommodityById(entry.getKey());
                int quantity = entry.getValue();

                BuyListItem buyListItem = new BuyListItem(commodity, quantity);
                purchasedListItems.add(buyListItem);
            }
            return new ResponseEntity<>(purchasedListItems, HttpStatus.OK);

        } catch (NotExistentUser | NotExistentCommodity ignored) {
            return new ResponseEntity<>(purchasedListItems, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/add")
    public ResponseEntity<String> addToBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            baloot.addCommodityToUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
        } catch (NotExistentUser | NotExistentCommodity e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/buy-list/remove")
    public ResponseEntity<String> removeFromBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            baloot.removeCommodityFromUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity removed from buy list successfully!", HttpStatus.OK);
        } catch (MissingUserId | MissingCommodityId | NotExistentUser | NotExistentCommodity |
                 CommodityIsNotInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/purchase")
    public ResponseEntity<String> purchaseBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            User user = baloot.getUserById(username);
            baloot.withdrawPayableAmount(user);
            return new ResponseEntity<>("buy list purchased successfully!", HttpStatus.OK);
        } catch (InsufficientCredit | NotExistentUser | NotInStock e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
