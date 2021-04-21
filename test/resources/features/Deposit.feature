Feature: Deposit money to an account
  Everybody wants money in their account

  Scenario: Tony Robinson deposits to it's checking account
    Given I am logged in as "Tony Robinson"
    When I try to deposit 300 to checking account
    Then I should have 4094.47 in checking

  Scenario: Tony Robinson deposits to it's savings account
    Given I am logged in as "Tony Robinson"
    When I try to deposit 250 to savings account
    Then I should have 1579.04 in savings

  Scenario: Tony Robinson deposits to it's credit account
    Given I am logged in as "Tony Robinson"
    When I try to deposit 1010.58 to credit account
    Then I should have -1443.78 in credit
