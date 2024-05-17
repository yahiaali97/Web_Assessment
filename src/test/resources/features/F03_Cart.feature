Feature: Adding and verifying products in the cart

  Scenario: Add products to the cart and verify items
    Given User is on the home page
    When User adds the first product to the cart
    And User adds the second product to the cart
    And User goes to the cart screen
    Then User verifies the prices of the products in the cart
    And User verifies the total price in the cart
    And User clicks on a placeholder button
    And User verifies the total price in the placeholder
    And User fills in the required details for purchase
    And User clicks on the purchase button
    Then User verifies the success message for the purchase