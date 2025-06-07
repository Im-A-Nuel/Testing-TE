Feature: Order and Cart Feature

  Scenario: Failed login with invalid credential
    Given the user is on the login page
    When the user enters an invalid username and password
    And the user clicks the login button
    Then the user should see a failed message

  Scenario: Successfully adding an item to cart
    Given the user is on the login page
    And the user is on the item page
    When the user add item to the cart
    And the user in the item list
    Then item should be seen in the item page

  Scenario: Successfully removing an item from cart
    Given the user is on the login page
    And the user is on the item page
    When the user add item to the cart
    And the user in the item list
    When the user remove item to the cart
    Then item shouldn't be seen in the item page

  Scenario: Checkout process with empty cart
    Given the user is on the login page
    And the user is on the cart page
    When the user tries to checkout
    Then the user should see a message "Cart is empty"

  Scenario: Logout after successful login
    Given the user is on the login page
    When the user enters a valid username and password
    And the user clicks the login button
    Then the user should see the home page
    When the user clicks the logout button
    Then the user should be redirected to the login page
