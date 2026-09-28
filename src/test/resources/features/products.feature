@products
Feature: Products and cart
  As a logged-in customer
  I want to browse, sort and add products to my cart

  Background:
    Given the user is logged in as a standard user

  @smoke
  Scenario: Add a single product to the cart
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1
    And the cart should contain "Sauce Labs Backpack"

  Scenario: Add and remove products
    When the user adds the following products to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show 3
    When the user removes "Sauce Labs Bike Light" from the cart
    Then the cart badge should show 2

  Scenario Outline: Sort products
    When the user sorts products by "<option>"
    Then the products should be sorted by "<option>"

    Examples:
      | option              |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |
