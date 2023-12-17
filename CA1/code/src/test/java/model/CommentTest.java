package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class CommentTest {

    private Comment comment;

    int id;
    private String userEmail;
    private String username;
    private int commodityId;
    private String text;

    @BeforeEach
    public void setupTest() {
        id = 1;
        userEmail = "first_user@gmail.com";
        username = "first_user";
        commodityId = 123;
        text = "Agha A-li";
        comment = new Comment(id, userEmail, username, commodityId, text);
    }

    @Test
    public void getDateNotNullTest() {
        String currentDate = comment.getDate();

        assertNotNull(currentDate, "Date should not be null");
    }

    @Test
    @DisplayName("This test check format of date with regex")
    public void dateFormatTest() {
        String currentDate = comment.getDate();

        String[] splitedCurrentDate = currentDate.split(" ");

        boolean isDayFormated = splitedCurrentDate[0].matches("^(19|20)\\d\\d([- /.])" +
                "(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])$");
        boolean isTimeFormated = splitedCurrentDate[1].matches("^(?:(?:([01]?" +
                "\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$");
        assertThat(isDayFormated).isTrue();
        assertThat(isTimeFormated).isTrue();
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Like", "Dislike", "disLike", "LIKE", "DISLIKE", "َOthers",
    })
    @DisplayName("Test invalid input for vote")
    public void testAddUserInvalidVote(String vote) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> comment.addUserVote("Alice", vote));
        assertEquals("The vote was not found", exception.getMessage());
    }

    @Test
    @DisplayName("A scenario to add votes and check do voters names write")
    public void addUserVoteContainsNameVoterTest() {
        comment.addUserVote("Ali", "like");
        assertThat(comment.getLike()).isEqualTo(1);
        assertThat(comment.getUserVote().containsKey("Ali")).isTrue();
        assertThat(comment.getUserVote().containsKey("Reza")).isFalse();
        comment.addUserVote("Reza", "dislike");
        assertThat(comment.getDislike()).isEqualTo(1);
        assertThat(comment.getUserVote().containsKey("Ali")).isTrue();
        assertThat(comment.getUserVote().containsKey("Reza")).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "reza reza_ale reza_asghar, dislike dislike dislike, 0, 3",
            "reza reza_ale reza_asghar, dislike like like, 2, 1",
            "reza reza_ale reza_asghar, dislike like dislike, 1, 2",
            "reza reza_ale reza_asghar, like like like, 3, 0",
    })
    @DisplayName("Count like and dislikes in Multiple Valid Votes from Multiple Different Users that specified")
    public void addUserVoteCountVotesTest(String voters, String votes, int expectedLikes, int expectedDislikes) throws IllegalArgumentException {
        List<String> splitedVoters = Arrays.asList(voters.split("\\s+"));
        List<String> splitedVotes = Arrays.asList(votes.split("\\s+"));

        for (int i = 0; i < splitedVoters.size(); i++)
            comment.addUserVote(splitedVoters.get(i), splitedVotes.get(i));

        int actualLikes = comment.getLike();
        int actualDislikes = comment.getDislike();

        assertEquals(expectedLikes, actualLikes);
        assertEquals(expectedDislikes, actualDislikes);
    }

    @Test
    public void addUserVoteFromSameUsersTest() {
        comment.addUserVote("Ali", "like");
        comment.addUserVote("Ali", "dislike");
        assertThat(comment.getUserVote().containsKey("Ali")).isTrue();
        assertThat(comment.getLike()).isEqualTo(0);
        assertThat(comment.getDislike()).isEqualTo(1);
    }
}
