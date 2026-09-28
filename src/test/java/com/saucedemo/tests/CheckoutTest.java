package com.saucedemo.tests;

import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test(groups = {"smoke", "e2e"}, description = "User can buy a product end to end")
    public void completeOrder() {
        InventoryPage inventory = new LoginPage(driver()).open()
                .loginAs(ConfigReader.get("standardUser"), ConfigReader.get("password"));

        inventory.addProductToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventory.getCartCount(), 1);

        CheckoutPage checkout = inventory.openCart().checkout()
                .enterCustomerInfo("John", "Doe", "10001")
                .clickContinue();

        Assert.assertEquals(checkout.getTotal(), checkout.getItemTotal() + checkout.getTax(), 0.01);

        checkout.clickFinish();
        Assert.assertTrue(checkout.getConfirmationMessage().equalsIgnoreCase("Thank you for your order!"));
    }
}
