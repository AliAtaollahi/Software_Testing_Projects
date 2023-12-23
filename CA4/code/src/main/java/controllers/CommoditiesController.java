package controllers;

import service.Baloot;
import model.Comment;
import model.Commodity;
import model.User;
import exceptions.NotExistentCommodity;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class CommoditiesController {

    private Baloot baloot = Baloot.getInstance();

    public void setBaloot(Baloot baloot) {
        this.baloot = baloot;
    }
    @GetMapping(value = "/commodities")
    public ResponseEntity<ArrayList<Commodity>> getCommodities() {
        return new ResponseEntity<>(baloot.getCommodities(), HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}")
    public ResponseEntity<Commodity> getCommodity(@PathVariable String id) {
        try {
            Commodity commodity = baloot.getCommodityById(id);
            return new ResponseEntity<>(commodity, HttpStatus.OK);
        } catch (NotExistentCommodity e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/commodities/{id}/rate")
    public ResponseEntity<String> rateCommodity(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            int rate = Integer.parseInt(input.get("rate"));
            String username = input.get("username");
            Commodity commodity = baloot.getCommodityById(id);
            commodity.addRate(username, rate);
            return new ResponseEntity<>("rate added successfully!", HttpStatus.OK);
        } catch (NotExistentCommodity e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/commodities/{id}/comment")
    public ResponseEntity<String> addCommodityComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = baloot.generateCommentId();
        String username = input.get("username");
        String commentText = input.get("comment");

        User user = null;
        try {
            user = baloot.getUserById(username);
        } catch (NotExistentUser ignored) {
        }

        Comment comment = new Comment(commentId, user.getEmail(), user.getUsername(), Integer.parseInt(id), commentText);
        baloot.addComment(comment);

        return new ResponseEntity<>("comment added successfully!", HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}/comment")
    public ResponseEntity<ArrayList<Comment>> getCommodityComment(@PathVariable String id) {
        ArrayList<Comment> comments = baloot.getCommentsForCommodity(Integer.parseInt(id));

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping(value = "/commodities/search")
    public ResponseEntity<ArrayList<Commodity>> searchCommodities(@RequestBody Map<String, String> input) {
        String searchOption = input.get("searchOption");
        String searchValue = input.get("searchValue");

        ArrayList<Commodity> commodities = switch (searchOption) {
            case "name" -> baloot.filterCommoditiesByName(searchValue);
            case "category" -> baloot.filterCommoditiesByCategory(searchValue);
            case "provider" -> baloot.filterCommoditiesByProviderName(searchValue);
            default -> new ArrayList<>();
        };

        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}/suggested")
    public ResponseEntity<ArrayList<Commodity>> getSuggestedCommodities(@PathVariable String id) {
        try {
            Commodity commodity = baloot.getCommodityById(id);
            ArrayList<Commodity> suggestedCommodities = baloot.suggestSimilarCommodities(commodity);
            return new ResponseEntity<>(suggestedCommodities, HttpStatus.OK);
        } catch (NotExistentCommodity ignored) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

}