package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Example of the same framework used with plain TestNG (no Cucumber). */
public class LoginTest extends BaseTest {

    @Test(groups = "smoke", description = "Standard user can log in")
    public void validLogin() {
        InventoryPage inventory = new LoginPage(driver()).open()
                .loginAs(ConfigReader.get("standardUser"), ConfigReader.get("password"));

        Assert.assertTrue(inventory.isLoaded(), "Products page should be shown after login");
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "wrong_pass", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
        };
    }

    @Test(dataProvider = "invalidLogins", groups = "regression", description = "Invalid logins show the right error")
    public void invalidLogin(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver()).open();
        loginPage.loginAs(username, password);

        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }
}
