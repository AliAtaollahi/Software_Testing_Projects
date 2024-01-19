package model;

import exceptions.*;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CucumberSteps {
    private Commodity commodity;
    private Exception exception;
    private User user;

    public static User createAnonymousUser() {
        return new User("melika", 
        "dastjerdi", 
        "atask_melik@gmail.com", 
        "2001-09-09 11:11:11", 
        "Tehran");
    }

    @Then("Update the purchase list to:")
    public void buyListShouldBe(List<List<String>> expectedBuyList) {
        if (expectedBuyList.isEmpty())
            assertTrue(user.getBuyList().isEmpty());
        else
            expectedBuyList.forEach(row -> {
                String commodityId = row.get(0);
                int expectedQuantity = Integer.parseInt(row.get(1));
                assertEquals(expectedQuantity, user.getBuyList().get(commodityId));
            });
    }

    @Then("Raise a CommodityNotInBuyList exception")
    public void commodityIsNotInBuyListExceptionShouldBeThrown() {
        assertExceptionType(CommodityIsNotInBuyList.class);
    }

    @Then("Ensure the credit balance is {float}")
    public void creditBalanceShouldBe(float expectedCredit) {
        assertCreditBalance(expectedCredit);
    }

    @Then("Throw an InsufficientCredit exception")
    public void insufficientCreditExceptionShouldBeThrown() {
        assertExceptionType(InsufficientCredit.class);
    }

    @Then("Throw an InvalidWithdrawException exception")
    public void InvalidWithdrawExceptionExceptionShouldBeThrown() {
        assertExceptionType(InvalidWithdrawException.class);
    }

    @Then("Verify the updated credit balance is {float}")
    public void newCreditBalanceShouldBe(float expectedCredit) {
        assertCreditBalance(expectedCredit);
    }

    @Then("Raise an InvalidCreditRange exception")
    public void invalidCreditRangeExceptionShouldBeThrown() {
        assertExceptionType(InvalidCreditRange.class);
    }

    @Given("A user possessing a credit balance of {float}")
    public void aUserWithCredit(float initialCredit) {
        user = createAnonymousUser();
        user.setCredit(initialCredit);
    }

    @Given("An unidentified user with the specified purchase list:")
    public void anonymousUserWithBuyList(List<List<String>> buyListData) {
        user = new User("username",
                "password",
                "email",
                "birthDate",
                "address");
        Map<String, Integer> buyList = new HashMap<>();
        buyListData.forEach(row -> {
            String commodityId = row.get(0);
            int quantity = Integer.parseInt(row.get(1));
            buyList.put(commodityId, quantity);
        });
        user.setBuyList(buyList);
    }

    @When("The user initiates a withdrawal of {float}")
    public void userWithdraws(float withdrawnAmount) {
        try {
            user.withdrawCredit(withdrawnAmount);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @When("The user supplements their existing credit balance with {float}")
    public void userAddsToCurrentCreditBalance(float amount) {
        try {
            user.addCredit(amount);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @When("The user eliminates the product with ID {string} from the purchase list")
    public void userRemovesFromBuyList(String commodityId) {
        this.commodity = new Commodity();
        commodity.setId(commodityId);
        commodity.setPrice(400);
        commodity.setName("Product");
        try {
            user.removeItemFromBuyList(commodity);
        } catch (CommodityIsNotInBuyList e) {
            this.exception = e;
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    private void assertExceptionType(Class<? extends Exception> expectedExceptionType) {
        assertNotNull(exception);
        assertTrue(expectedExceptionType.isInstance(exception));
    }

    private void assertCreditBalance(float expectedCredit) {
        float actualCredit = user.getCredit();
        assertEquals(expectedCredit, actualCredit);
    }
}
