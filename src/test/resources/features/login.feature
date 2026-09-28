@login
Feature: Login
  As a SauceDemo customer
  I want to log in with my account
  So that I can shop for products

  Background:
    Given the user is on the login page

  @smoke
  Scenario: Successful login with a valid user
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the products page should be displayed

  @negative
  Scenario Outline: Login fails with invalid details
    When the user logs in with username "<username>" and password "<password>"
    Then the login error message should be "<error>"

    Examples:
      | username        | password     | error                                                                     |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | standard_user   | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce | Epic sadface: Username is required                                        |
      | standard_user   |              | Epic sadface: Password is required                                        |

  @smoke
  Scenario: User can log out
    When the user logs in with username "standard_user" and password "secret_sauce"
    And the user logs out
    Then the login page should be displayed
