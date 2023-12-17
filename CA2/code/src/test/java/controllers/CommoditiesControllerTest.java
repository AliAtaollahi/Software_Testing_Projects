package controllers;

import exceptions.NotExistentCommodity;
import exceptions.NotExistentUser;
import model.Comment;
import model.Commodity;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.Baloot;

import java.util.ArrayList;
import java.util.Map;

import static defines.Errors.NOT_EXISTENT_COMMODITY;
import static defines.Errors.NOT_EXISTENT_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommoditiesControllerTest {
    @Mock
    Baloot baloot;

    @Captor
    ArgumentCaptor<Comment> commentArgumentCaptor;

    CommoditiesController commoditiesController;

    @BeforeEach
    public void setup() {
        commoditiesController = new CommoditiesController();
        commoditiesController.setBaloot(baloot);
    }

    public static Commodity createFakeCommodity() {
        Commodity commodity = new Commodity();
        commodity.setId(String.valueOf(1));
        commodity.setName("product " + 1);
        return commodity;
    }

    public static Comment createFakeComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("a comment");
        return comment;
    }

    @Test
    @DisplayName("Test the retrieval of all commodities - should return a list of available commodities")
    public void getCommoditiesTest() {
        ArrayList<Commodity> commoditiesFake = new ArrayList<>();
        commoditiesFake.add(createFakeCommodity());

        when(baloot.getCommodities()).thenReturn(commoditiesFake);
        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.getCommodities();

        verify(baloot, times(1)).getCommodities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commoditiesFake.size());
        assertEquals(response.getBody().get(0).getId(), commoditiesFake.get(0).getId());
        assertEquals(response.getBody().get(0).getName(), commoditiesFake.get(0).getName());
    }

    @Test
    @DisplayName("Test the retrieval of a specific commodity by ID - should return the specific commodity")
    public void getCommodityTest() throws NotExistentCommodity {
        Commodity commodity = createFakeCommodity();

        when(baloot.getCommodityById(commodity.getId())).thenReturn(commodity);
        ResponseEntity<Commodity> response = commoditiesController.getCommodity(commodity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commodity.getId(), response.getBody().getId());
        assertEquals(commodity.getName(), response.getBody().getName());
    }

    @Test
    @DisplayName("Test retrieval of an unavailable commodity by ID - should return a 'Not Found' status")
    public void getUnavailableCommodityTest() throws NotExistentCommodity {
        String commodityId = "1";

        when(baloot.getCommodityById(commodityId)).thenThrow(new NotExistentCommodity());

        ResponseEntity<Commodity> response = commoditiesController.getCommodity(commodityId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test rating a valid commodity - should successfully add the user's rating to the commodity")
    public void rateCommodityTest() throws NotExistentCommodity {
        Map<String, String> fakeInput = Map.of("rate", "1",
                                                "username", "ahmad");
        Commodity commodityFake = mock(Commodity.class);
        String commodityId = "1";

        when(baloot.getCommodityById(commodityId)).thenReturn(commodityFake);

        ResponseEntity<String> response = commoditiesController.rateCommodity(commodityId, fakeInput);

        verify(commodityFake, times(1)).addRate(fakeInput.get("username"),
                Integer.parseInt(fakeInput.get("rate")));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("rate added successfully!", response.getBody());
    }

    @Test
    @DisplayName("Test rating an invalid commodity - should return a 'Bad Request' status")
    public void rateInvalidCommodityTest() throws NotExistentCommodity {
        Map<String, String> fakeInput = Map.of("rate", "no rate",
                "username", "ahmad");

        String commodityId = "1";

        ResponseEntity<String> response = commoditiesController.rateCommodity(commodityId, fakeInput);

        verify(baloot, times(0)).getCommodityById(commodityId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Test rating an unavailable commodity - should return a 'Not Found' status")
    public void rateUnavailableCommodityTest() throws NotExistentCommodity {
        Map<String, String> fakeInput = Map.of("rate", "1",
                "username", "ahmad");
        String rateString = "5";
        String username = "ali";
        String commodityId = "1";

        when(baloot.getCommodityById(commodityId)).thenThrow(new NotExistentCommodity());

        ResponseEntity<String> response = commoditiesController.rateCommodity(commodityId, fakeInput);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_EXISTENT_COMMODITY, response.getBody());
    }


    @Test
    @DisplayName("Test adding a comment to a valid commodity - should successfully add the comment")
    public void addCommodityCommentTest() throws NotExistentCommodity, NotExistentUser {
        Map<String, String> fakeInput = Map.of("comment", "no comment",
                "username", "ahmad");

        String commodityId = "1";
        String username = fakeInput.get("username");
        String commentText = fakeInput.get("comment");
        String userEmail = username + "@test.ir";
        User userFake = new User(username, "", userEmail, "", "");

        when(baloot.getUserById(username)).thenReturn(userFake);

        ResponseEntity<String> response = commoditiesController.addCommodityComment(commodityId, fakeInput);

        verify(baloot).addComment(commentArgumentCaptor.capture());

        Comment comment = commentArgumentCaptor.getValue();

        assertEquals(userEmail, comment.getUserEmail());
        assertEquals(username, comment.getUsername());
        assertEquals(commentText, comment.getText());
        assertEquals(commodityId, String.valueOf(comment.getCommodityId()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("comment added successfully!", response.getBody());
    }

    @Test
    @DisplayName("Test adding a comment to an invalid commodity - should return a 'Bad Request' status")
    public void addInvalidommodityCommentTest() throws NotExistentUser {
        Map<String, String> fakeInput = Map.of("comment", "no comment",
                "username", "ahmad");
        String commodityId = "adasdsa";

        when(baloot.getUserById(fakeInput.get("username"))).thenReturn(new User());

        ResponseEntity<String> response = commoditiesController.addCommodityComment(commodityId, fakeInput);

        verify(baloot, times(0)).addComment(any(Comment.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Test adding a comment to an unavailable commodity - should return a 'Not Found' status")
    public void addUnavailableCommodityCommentTest() throws NotExistentUser {
        Map<String, String> fakeInput = Map.of("comment", "no comment",
                "username", "ahmad");
        String commodityId = "1";

        when(baloot.getUserById(fakeInput.get("username"))).thenThrow(new NotExistentUser());

        ResponseEntity<String> response = commoditiesController.addCommodityComment(commodityId, fakeInput);

        verify(baloot, times(0)).addComment(any(Comment.class));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_EXISTENT_USER, response.getBody());
    }

    @Test
    @DisplayName("Test retrieving comments for a valid commodity - should return a list of comments")
    public void getCommodityCommentTest() {
        ArrayList<Comment> commentsFake = new ArrayList<>();
        commentsFake.add(createFakeComment());

        int commodityId = 1;

        when(baloot.getCommentsForCommodity(commodityId)).thenReturn(commentsFake);
        ResponseEntity<ArrayList<Comment>> response = commoditiesController.getCommodityComment(String.valueOf(commodityId));

        verify(baloot, times(1)).getCommentsForCommodity(commodityId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commentsFake.size());
        assertEquals(response.getBody().get(0).getId(), commentsFake.get(0).getId());
        assertEquals(response.getBody().get(0).getText(), commentsFake.get(0).getText());
    }

    @Test
    @DisplayName("Test retrieving comments for an invalid commodity - should return a 'Bad Request' status")
    public void getInValidCommodityCommentTest() {
        String commodityId = "not number";

        ResponseEntity<ArrayList<Comment>> response = commoditiesController.getCommodityComment(commodityId);

        verify(baloot, times(0)).getCommentsForCommodity(anyInt());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Test searching commodities by name - should return a list of matching commodities")
    public void searchByNameCommoditiesTest() {
        Map<String, String> fakeInput = Map.of("searchOption", "name",
                "searchValue", "ahmad");
        ArrayList<Commodity> commoditiesFake = new ArrayList<>();
        commoditiesFake.add(createFakeCommodity());

        when(baloot.filterCommoditiesByName(fakeInput.get("searchValue"))).thenReturn(commoditiesFake);

        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.searchCommodities(fakeInput);

        verify(baloot, times(0)).filterCommoditiesByCategory(anyString());
        verify(baloot, times(0)).filterCommoditiesByProviderName(anyString());
        verify(baloot, times(1)).filterCommoditiesByName(fakeInput.get("searchValue"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commoditiesFake.size());
        assertEquals(response.getBody().get(0).getId(), commoditiesFake.get(0).getId());
        assertEquals(response.getBody().get(0).getName(), commoditiesFake.get(0).getName());
    }

    @Test
    @DisplayName("Test searching commodities by category - should return a list of commodities under the specified category")
    public void searchByCategoryCommoditiesTest() {
        Map<String, String> fakeInput = Map.of("searchOption", "category",
                "searchValue", "ahmad");
        ArrayList<Commodity> commoditiesFake = new ArrayList<>();
        commoditiesFake.add(createFakeCommodity());

        when(baloot.filterCommoditiesByCategory(fakeInput.get("searchValue"))).thenReturn(commoditiesFake);

        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.searchCommodities(fakeInput);

        verify(baloot, times(0)).filterCommoditiesByName(anyString());
        verify(baloot, times(0)).filterCommoditiesByProviderName(anyString());
        verify(baloot, times(1)).filterCommoditiesByCategory(fakeInput.get("searchValue"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commoditiesFake.size());
        assertEquals(response.getBody().get(0).getId(), commoditiesFake.get(0).getId());
        assertEquals(response.getBody().get(0).getName(), commoditiesFake.get(0).getName());
    }

    @Test
    @DisplayName("Test searching commodities by provider - should return a list of commodities from the specified provider")
    public void searchByProviderCommoditiesTest() {
        Map<String, String> fakeInput = Map.of("searchOption", "provider",
                "searchValue", "ahmad");

        ArrayList<Commodity> commoditiesFake = new ArrayList<>();
        commoditiesFake.add(createFakeCommodity());

        when(baloot.filterCommoditiesByProviderName(fakeInput.get("searchValue"))).thenReturn(commoditiesFake);

        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.searchCommodities(fakeInput);

        verify(baloot, times(0)).filterCommoditiesByName(anyString());
        verify(baloot, times(0)).filterCommoditiesByCategory(anyString());
        verify(baloot, times(1)).filterCommoditiesByProviderName(fakeInput.get("searchValue"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commoditiesFake.size());
        assertEquals(response.getBody().get(0).getId(), commoditiesFake.get(0).getId());
        assertEquals(response.getBody().get(0).getName(), commoditiesFake.get(0).getName());
    }

    @Test
    @DisplayName("Test searching commodities by an invalid option - should return an empty list")
    public void searchByInvalidOptionCommoditiesTest() {
        Map<String, String> fakeInput = Map.of("searchOption", "this option is not in oprions!",
                "searchValue", "ahmad");

        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.searchCommodities(fakeInput);

        verify(baloot, times(0)).filterCommoditiesByName(anyString());
        verify(baloot, times(0)).filterCommoditiesByCategory(anyString());
        verify(baloot, times(0)).filterCommoditiesByProviderName(fakeInput.get("searchValue"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), 0);
    }


    @Test
    @DisplayName("Test getting suggested commodities for an existing commodity - should return a list of suggested similar commodities")
    public void getSuggestedCommoditiesExistTest() throws NotExistentCommodity {
        Commodity commodityFake = createFakeCommodity();

        ArrayList<Commodity> commoditiesFake = new ArrayList<>();
        commoditiesFake.add(createFakeCommodity());
        commoditiesFake.add(createFakeCommodity());

        when(baloot.getCommodityById(commodityFake.getId())).thenReturn(commodityFake);
        when(baloot.suggestSimilarCommodities(commodityFake)).thenReturn(commoditiesFake);
        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.getSuggestedCommodities(commodityFake.getId());

        assertEquals(response.getBody().get(0).getId(), commoditiesFake.get(0).getId());
        assertEquals(response.getBody().get(0).getName(), commoditiesFake.get(0).getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().size(), commoditiesFake.size());
    }

    @Test
    @DisplayName("Test getting suggested commodities for a non-existent commodity - should return a 'Not Found' status")
    public void getSuggestedCommoditiesNotExistTest() throws NotExistentCommodity {
        String commodityId = "1";

        when(baloot.getCommodityById(commodityId)).thenThrow(new NotExistentCommodity());

        ResponseEntity<ArrayList<Commodity>> response = commoditiesController.getSuggestedCommodities(commodityId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
