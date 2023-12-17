package database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Comment;
import model.Commodity;
import model.Provider;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DataParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String dataPath = "src/main/java/database/data/";
    Database database;

    public DataParser(Database database) {
        this.database = database;
    }

    public void getUsersList() throws IOException {
        String filePath = dataPath + "users.json";
        String usersString = new String(Files.readAllBytes(Paths.get(filePath)));

        List<User> userList = objectMapper.readValue(usersString, new TypeReference<>() {
        });
        database.setUsers((ArrayList<User>) userList);
    }

    public void getProvidersList() throws IOException {
        String filePath = dataPath + "providers.json";
        String providersString = new String(Files.readAllBytes(Paths.get(filePath)));

        List<Provider> providerList = objectMapper.readValue(providersString, new TypeReference<>() {
        });
        database.setProviders((ArrayList<Provider>) providerList);
    }

    public void getCommoditiesList() throws IOException {
        String filePath = dataPath + "commodities.json";
        String commoditiesString = new String(Files.readAllBytes(Paths.get(filePath)));

        List<Commodity> commodityList = objectMapper.readValue(commoditiesString, new TypeReference<>() {
        });
        database.setCommodities((ArrayList<Commodity>) commodityList);
    }

    public void getCommentsList() throws IOException {
        String filePath = dataPath + "comments.json";
        String commentsString = new String(Files.readAllBytes(Paths.get(filePath)));

        List<Comment> commentsList = objectMapper.readValue(commentsString, new TypeReference<>() {
        });
        database.setComments((ArrayList<Comment>) commentsList);

        for (Comment comment : commentsList)
            comment.setId(commentsList.indexOf(comment));
    }
}
