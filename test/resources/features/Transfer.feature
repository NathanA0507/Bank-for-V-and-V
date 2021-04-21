Feature: Transfer money to an account
  User wants to share money

  Scenario: Janet Bellamy transfers money from checking to Tony Robinson's checking
    Given I am logged in as "Janet Bellamy"
    When I try to transfer 1000 to "Tony Robinson" checking account
    Then I should have 3236.90 in checking account
    And Tony Robinson should have 5094.47 in checking