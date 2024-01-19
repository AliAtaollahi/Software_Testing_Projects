Feature: User addCredit Feature

  Scenario: Add valid positive credit amount
    Given A user possessing a credit balance of 300.0
    When The user supplements their existing credit balance with 100.0
    Then Verify the updated credit balance is 400.0

  Scenario: Add valid zero credit amount (shouldn't change anything)
    Given A user possessing a credit balance of 660.0
    When The user supplements their existing credit balance with 0.0
    Then Verify the updated credit balance is 660.0

  Scenario: Attempt to add invalid negative credit amount
    Given A user possessing a credit balance of 200.0
    When The user supplements their existing credit balance with -150.0
    Then Raise an InvalidCreditRange exception

