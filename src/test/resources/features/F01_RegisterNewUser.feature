Feature: Register new user

  Scenario: Successful user registration
    Given I am on the home page for signing up
    When I click the sign up button
    And I wait for the sign-up form to be visible
    And I enter the user credentials from the test data
    And I submit the sign-up form
    Then I should see a success alert with the message "Sign up successful."