package controllers;

import service.Baloot;
import utils.BuyListItem;
import model.Commodity;
import model.User;
import exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class BuyListController {
    @PostMapping(value = "/buy-list")
    public ResponseEntity<ArrayList<BuyListItem>> getBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> buyListItems = new ArrayList<>();

        try {
            Map<String, Integer> buyList = Baloot.getInstance().getUserBuyList(username);
            for (Map.Entry<String, Integer> entry : buyList.entrySet()) {
                Commodity commodity = Baloot.getInstance().getCommodityById(entry.getKey());
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
            Map<String, Integer> purchasedList = Baloot.getInstance().getUserPurchasedList(username);
            for (Map.Entry<String, Integer> entry : purchasedList.entrySet()) {
                Commodity commodity = Baloot.getInstance().getCommodityById(entry.getKey());
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
    public ResponseEntity<String> addToBuyList(@RequestBody Map<String, String> input) throws NotInStock {
        String username = input.get("username");
        try {
            Baloot.getInstance().addCommodityToUserBuyList(username, input.get("id"));
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
            Baloot.getInstance().removeCommodityFromUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
        } catch (MissingUserId | MissingCommodityId | NotExistentUser | NotExistentCommodity |
                 CommodityIsNotInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/purchase")
    public ResponseEntity<String> purchaseBuyList(@RequestBody Map<String, String> input) throws InvalidWithdrawException, InvalidNumQuantityException {
        String username = input.get("username");
        try {
            User user = Baloot.getInstance().getUserById(username);
            Baloot.getInstance().withdrawPayableAmount(user);
            return new ResponseEntity<>("buy list purchased successfully!", HttpStatus.OK);
        } catch (InsufficientCredit | NotExistentUser | NotInStock e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
