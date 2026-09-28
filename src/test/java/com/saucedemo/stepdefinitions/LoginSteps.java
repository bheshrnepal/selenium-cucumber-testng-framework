package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage() {
        return new LoginPage(DriverFactory.getDriver());
    }

    private InventoryPage inventoryPage() {
        return new InventoryPage(DriverFactory.getDriver());
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage().open();
    }

    @Given("the user is logged in as a standard user")
    public void theUserIsLoggedInAsAStandardUser() {
        InventoryPage inventory = loginPage().open()
                .loginAs(ConfigReader.get("standardUser"), ConfigReader.get("password"));
        Assert.assertTrue(inventory.isLoaded(), "Products page did not load after login");
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsIn(String username, String password) {
        loginPage().loginAs(username, password);
    }

    @When("the user logs out")
    public void theUserLogsOut() {
        inventoryPage().logout();
    }

    @Then("the products page should be displayed")
    public void theProductsPageShouldBeDisplayed() {
        Assert.assertTrue(inventoryPage().isLoaded(), "Products page is not displayed");
        Assert.assertTrue(inventoryPage().getCurrentUrl().contains("inventory.html"));
    }

    @Then("the login error message should be {string}")
    public void theLoginErrorMessageShouldBe(String expected) {
        Assert.assertEquals(loginPage().getErrorMessage(), expected);
    }

    @Then("the login page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        Assert.assertTrue(loginPage().isLoginButtonDisplayed(), "Login button is not visible");
    }
}
