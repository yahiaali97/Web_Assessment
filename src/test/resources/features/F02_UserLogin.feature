Feature: User Login

  Scenario: User successfully logs in with valid credentials
    Given User is on the homepage to login
    When User clicks on the login button
    And User waits for the login form to appear
    And User enters username and password
    And User submits the login form
    Then User should see a welcome message with the username