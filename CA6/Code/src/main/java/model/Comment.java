package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Comment {

    int id;
    private String userEmail;
    private String username;
    private int commodityId;
    private String text;
    private String date;

    private int like;
    private int dislike;
    private Map<String, String> userVote = new HashMap<>();

    public Comment(int id, String userEmail, String username, int commodityId, String text) {
        this.id = id;
        this.userEmail = userEmail;
        this.username = username;
        this.commodityId = commodityId;
        this.text = text;
        this.date = getCurrentDate();
    }

    public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }

    public void addUserVote(String userName, String vote) {
        userVote.put(userName, vote);

        this.like = 0;
        this.dislike = 0;

        for (String key : userVote.keySet()) {
            if (userVote.get(key).equals("like"))
                this.like += 1;
            else if (userVote.get(key).equals("dislike"))
                this.dislike += 1;
        }
    }

}
