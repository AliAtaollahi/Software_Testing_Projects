Feature: Credit Withdrawal Feature

  Scenario: Withdrawal with Sufficient Credit
    Given A user possessing a credit balance of 2300.0
    When The user initiates a withdrawal of 300.0
    Then Ensure the credit balance is 2000.0

  Scenario: Withdrawal with Negative Amount
    Given A user possessing a credit balance of 2000.0
    When The user initiates a withdrawal of -400.0
    Then Throw an InvalidWithdrawException exception

  Scenario: Withdrawal with Insufficient Credit
    Given A user possessing a credit balance of 4400.0
    When The user initiates a withdrawal of 6000.0
    Then Throw an InsufficientCredit exception
