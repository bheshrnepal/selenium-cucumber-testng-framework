package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/** The "Products" page shown after a successful login. */
public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");
    private final By sortDropdown = By.cssSelector(".product_sort_container");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public boolean isLoaded() {
        return isVisibleWithinTimeout(pageTitle) && getTitle().equalsIgnoreCase("Products");
    }

    public List<String> getProductNames() {
        waitForVisible(itemNames);
        return driver.findElements(itemNames).stream().map(WebElement::getText).toList();
    }

    public List<Double> getProductPrices() {
        waitForVisible(itemPrices);
        return driver.findElements(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    /**
     * Sort options shown in the dropdown:
     * "Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"
     */
    public void sortBy(String visibleText) {
        new Select(waitForVisible(sortDropdown)).selectByVisibleText(visibleText);
    }

    public void addProductToCart(String productName) {
        click(buttonFor(productName));
    }

    public void removeProductFromCart(String productName) {
        click(buttonFor(productName));
    }

    public int getCartCount() {
        if (!isDisplayed(cartBadge)) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        click(menuButton);
        click(logoutLink);
        return new LoginPage(driver);
    }

    /** The Add/Remove button that belongs to a product, found by the product's name. */
    private By buttonFor(String productName) {
        return By.xpath("//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']"
                + "/ancestor::div[contains(@class,'inventory_item_description')]//button");
    }
}
