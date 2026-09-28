@checkout
Feature: Checkout
  As a customer with items in my cart
  I want to complete checkout
  So that I can place my order

  Background:
    Given the user is logged in as a standard user
    And the user adds "Sauce Labs Backpack" to the cart
    And the user goes to the cart and starts checkout

  @smoke @e2e
  Scenario: Complete an order end to end
    When the user enters checkout details "John", "Doe" and "10001"
    Then the order total should equal item total plus tax
    When the user finishes the order
    Then the confirmation message should be "Thank you for your order!"

  @negative
  Scenario Outline: Checkout requires all customer details
    When the user enters checkout details "<first>", "<last>" and "<zip>"
    Then the checkout error message should be "<error>"

    Examples:
      | first | last  | zip   | error                          |
      |       | Doe   | 10001 | Error: First Name is required  |
      | John  |       | 10001 | Error: Last Name is required   |
      | John  | Doe   |       | Error: Postal Code is required |
