package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSteps {

    private InventoryPage inventoryPage() {
        return new InventoryPage(DriverFactory.getDriver());
    }

    @When("the user adds {string} to the cart")
    public void theUserAddsToTheCart(String product) {
        inventoryPage().addProductToCart(product);
    }

    @When("the user adds the following products to the cart:")
    public void theUserAddsTheFollowingProducts(List<String> products) {
        InventoryPage page = inventoryPage();
        products.forEach(page::addProductToCart);
    }

    @When("the user removes {string} from the cart")
    public void theUserRemovesFromTheCart(String product) {
        inventoryPage().removeProductFromCart(product);
    }

    @When("the user sorts products by {string}")
    public void theUserSortsProductsBy(String option) {
        inventoryPage().sortBy(option);
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        Assert.assertEquals(inventoryPage().getCartCount(), expected);
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String product) {
        List<String> items = inventoryPage().openCart().getItemNames();
        Assert.assertTrue(items.contains(product), "Cart items were: " + items);
    }

    @Then("the products should be sorted by {string}")
    public void theProductsShouldBeSortedBy(String option) {
        InventoryPage page = inventoryPage();
        switch (option) {
            case "Name (A to Z)" -> assertSorted(page.getProductNames(), Comparator.naturalOrder());
            case "Name (Z to A)" -> assertSorted(page.getProductNames(), Comparator.reverseOrder());
            case "Price (low to high)" -> assertSorted(page.getProductPrices(), Comparator.naturalOrder());
            case "Price (high to low)" -> assertSorted(page.getProductPrices(), Comparator.reverseOrder());
            default -> throw new IllegalArgumentException("Unknown sort option: " + option);
        }
    }

    private <T> void assertSorted(List<T> actual, Comparator<? super T> order) {
        List<T> expected = new ArrayList<>(actual);
        expected.sort(order);
        Assert.assertEquals(actual, expected, "Products are not sorted correctly");
    }
}
