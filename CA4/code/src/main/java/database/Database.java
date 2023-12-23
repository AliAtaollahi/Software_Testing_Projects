package database;

import model.Comment;
import model.Commodity;
import model.Provider;
import model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Database {
    @Getter
    private static final Database instance = new Database();

    private Database() {
    }

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private ArrayList<Commodity> commodities = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
