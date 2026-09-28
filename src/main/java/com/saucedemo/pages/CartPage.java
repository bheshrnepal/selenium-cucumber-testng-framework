package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By cartItemNames = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return getText(pageTitle).equalsIgnoreCase("Your Cart");
    }

    public List<String> getItemNames() {
        waitForVisible(pageTitle);
        return driver.findElements(cartItemNames).stream().map(WebElement::getText).toList();
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        return new InventoryPage(driver);
    }
}
