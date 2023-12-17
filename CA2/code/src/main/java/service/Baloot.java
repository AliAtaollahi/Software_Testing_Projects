package service;

import database.DataParser;
import database.Database;
import model.Comment;
import model.Commodity;
import model.Provider;
import model.User;
import exceptions.*;

import java.io.IOException;
import java.util.*;


public class Baloot {
    private static Baloot instance;

    private Baloot() {
        fetchAndStoreData();
    }

    public static Baloot getInstance() {
        if (instance == null) {
            instance = new Baloot();
        }
        return instance;
    }

    public void fetchAndStoreData() {
        DataParser dataParser = new DataParser(Database.getInstance());

        try {
            dataParser.getUsersList();
            dataParser.getProvidersList();
            dataParser.getCommoditiesList();
            dataParser.getCommentsList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String userId, String password) throws NotExistentUser, IncorrectPassword {
        User user = this.getUserById(userId);
        if (!user.getPassword().equals(password))
            throw new IncorrectPassword();
    }

    public void addCommodityToUserBuyList(String userId, String commodityId)
            throws NotExistentUser, NotExistentCommodity, AlreadyInBuyList {
        User user = getUserById(userId);
        Commodity commodity = getCommodityById(commodityId);

        user.addBuyItem(commodity);
    }

    public void removeCommodityFromUserBuyList(String userId, String commodityId)
            throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity, CommodityIsNotInBuyList {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(commodityId);

        user.removeItemFromBuyList(commodity);
    }

    public float getCurrentBuyListPrice(User user) {
        float total = 0;
        for (var entry : new ArrayList<>(user.getBuyList().entrySet())) {
            try {
                Commodity commodity = getCommodityById(entry.getKey());
                total += commodity.getPrice() * entry.getValue();
            } catch (NotExistentCommodity ignored) {
            }
        }

        return total;
    }

    public void withdrawPayableAmount(User user) throws InsufficientCredit, NotInStock {
        float amount = getCurrentBuyListPrice(user);
        user.withdrawCredit(amount);

        for (var entry : new ArrayList<>(user.getBuyList().entrySet())) {
            user.addPurchasedItem(entry.getKey(), entry.getValue());
            try {
                Commodity commodity = getCommodityById(entry.getKey());
                commodity.updateInStock(-entry.getValue());
            } catch (NotExistentCommodity ignored) {
            } catch (NotInStock e) {
                throw new NotInStock();
            }
        }

        user.setBuyList(new HashMap<>());
    }

    public User getUserById(String userId) throws NotExistentUser {
        for (User user : Database.getInstance().getUsers())
            if (user.getUsername().equals(userId))
                return user;

        throw new NotExistentUser();
    }

    public Provider getProviderById(String providerId) throws NotExistentProvider {
        for (Provider provider : Database.getInstance().getProviders())
            if (Objects.equals(provider.getId(), providerId))
                return provider;

        throw new NotExistentProvider();
    }

    public Commodity getCommodityById(String commodityId) throws NotExistentCommodity {
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (Objects.equals(commodity.getId(), commodityId))
                return commodity;

        throw new NotExistentCommodity();
    }

    public ArrayList<Commodity> getCommodities() {
        return Database.getInstance().getCommodities();
    }

    public ArrayList<Commodity> getCommoditiesProvidedByProvider(String providerId) {
        ArrayList<Commodity> commodities = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (Objects.equals(commodity.getProviderId(), providerId))
                commodities.add(commodity);

        return commodities;
    }

    public ArrayList<Comment> getCommentsForCommodity(int commodityId) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : Database.getInstance().getComments())
            if (comment.getCommodityId() == commodityId)
                comments.add(comment);

        return comments;
    }

    public Comment getCommentById(int commentId) throws NotExistentComment {
        for (Comment comment : Database.getInstance().getComments())
            if (comment.getId() == commentId)
                return comment;

        throw new NotExistentComment();
    }


    public ArrayList<Commodity> filterCommoditiesByCategory(String category) {
        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getCategories().contains(category))
                result.add(commodity);

        return result;
    }

    public ArrayList<Commodity> filterCommoditiesByName(String name) {
        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getName().contains(name))
                result.add(commodity);

        return result;
    }

    public ArrayList<Commodity> filterCommoditiesByProviderName(String name) {
        String providerId = "";
        for (Provider provider : Database.getInstance().getProviders()) {
            if (provider.getName().equals(name)) {
                providerId = provider.getId();
                break;
            }
        }

        ArrayList<Commodity> result = new ArrayList<>();

        if (Objects.equals(providerId, ""))
            return result;

        for (Commodity commodity : Database.getInstance().getCommodities())
            if (Objects.equals(commodity.getProviderId(), providerId))
                result.add(commodity);

        return result;
    }


    public Map<String, Integer> getUserBuyList(String userId) throws NotExistentUser {
        User user = getUserById(userId);
        return user.getBuyList();
    }

    public Map<String, Integer> getUserPurchasedList(String userId) throws NotExistentUser {
        User user = getUserById(userId);
        return user.getPurchasedList();
    }

    public void addUser(User user) throws UsernameAlreadyTaken {
        for (User user1 : Database.getInstance().getUsers())
            if (user1.getUsername().equals(user.getUsername()))
                throw new UsernameAlreadyTaken();

        Database.getInstance().addUser(user);
    }

    public void addComment(Comment comment) {
        Database.getInstance().addComment(comment);
    }

    public int generateCommentId() {
        return Database.getInstance().getComments().size();
    }

    public int isInSimilarCategoryWithFirstCommodity(Commodity c1, Commodity c2) {
        for (String category : c2.getCategories())
            if (c1.getCategories().contains(category))
                return 1;

        return 0;
    }

    public ArrayList<Commodity> suggestSimilarCommodities(Commodity commodity) {
        int MAX_NUMBER_OF_COMMODITY_SUGGESTIONS = 4;
        ArrayList<Commodity> results = new ArrayList<>();
        Hashtable<Commodity, Float> commodityScore = new Hashtable<>();

        for (Commodity commodity1 : Database.getInstance().getCommodities()) {
            if (commodity == commodity1)
                continue;

            float score = 11 * isInSimilarCategoryWithFirstCommodity(commodity, commodity1) + commodity1.getRating();
            commodityScore.put(commodity1, score);
        }

        List<Map.Entry<Commodity, Float>> list = new ArrayList<>(commodityScore.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        int count = 0;
        for (Map.Entry<Commodity, Float> entry : list) {
            results.add(entry.getKey());

            count += 1;
            if (count >= MAX_NUMBER_OF_COMMODITY_SUGGESTIONS)
                break;
        }

        return results;
    }

}
