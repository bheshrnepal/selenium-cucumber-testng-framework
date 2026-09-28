package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Covers the three checkout steps on SauceDemo:
 * 1) Your Information, 2) Overview, 3) Complete.
 */
public class CheckoutPage extends BasePage {

    // Step 1 - Your Information
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Step 2 - Overview
    private final By itemTotal = By.cssSelector(".summary_subtotal_label");
    private final By tax = By.cssSelector(".summary_tax_label");
    private final By total = By.cssSelector(".summary_total_label");
    private final By finishButton = By.id("finish");

    // Step 3 - Complete
    private final By completeHeader = By.cssSelector(".complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterCustomerInfo(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        return this;
    }

    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public double getItemTotal() {
        return parseAmount(getText(itemTotal));
    }

    public double getTax() {
        return parseAmount(getText(tax));
    }

    public double getTotal() {
        return parseAmount(getText(total));
    }

    public CheckoutPage clickFinish() {
        click(finishButton);
        return this;
    }

    public String getConfirmationMessage() {
        return getText(completeHeader);
    }

    public InventoryPage backHome() {
        click(backHomeButton);
        return new InventoryPage(driver);
    }

    /** Turns labels like "Item total: $29.99" into 29.99 */
    private double parseAmount(String label) {
        return Double.parseDouble(label.substring(label.indexOf('$') + 1));
    }
}
