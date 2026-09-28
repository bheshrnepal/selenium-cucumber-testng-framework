package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CheckoutSteps {

    private CheckoutPage checkoutPage() {
        return new CheckoutPage(DriverFactory.getDriver());
    }

    @When("the user goes to the cart and starts checkout")
    public void theUserGoesToTheCartAndStartsCheckout() {
        new InventoryPage(DriverFactory.getDriver()).openCart().checkout();
    }

    @When("the user enters checkout details {string}, {string} and {string}")
    public void theUserEntersCheckoutDetails(String firstName, String lastName, String postalCode) {
        checkoutPage().enterCustomerInfo(firstName, lastName, postalCode).clickContinue();
    }

    @When("the user finishes the order")
    public void theUserFinishesTheOrder() {
        checkoutPage().clickFinish();
    }

    @Then("the order total should equal item total plus tax")
    public void theOrderTotalShouldEqualItemTotalPlusTax() {
        CheckoutPage page = checkoutPage();
        Assert.assertEquals(page.getTotal(), page.getItemTotal() + page.getTax(), 0.01);
    }

    @Then("the confirmation message should be {string}")
    public void theConfirmationMessageShouldBe(String expected) {
        Assert.assertEquals(checkoutPage().getConfirmationMessage().toLowerCase(), expected.toLowerCase());
    }

    @Then("the checkout error message should be {string}")
    public void theCheckoutErrorMessageShouldBe(String expected) {
        Assert.assertEquals(checkoutPage().getErrorMessage(), expected);
    }
}
