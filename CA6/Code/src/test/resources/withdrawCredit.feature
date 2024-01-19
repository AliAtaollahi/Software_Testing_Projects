Feature: User removes items Feature

  Scenario: Remove a single item from the buy list
    Given An unidentified user with the specified purchase list:
      | 10     | 1     |
    When The user eliminates the product with ID "10" from the purchase list
    Then Update the purchase list to:
      |

  Scenario: Attempt to remove an item with quantity greater than 1
    Given An unidentified user with the specified purchase list:
      | 10     | 5     |
    When The user eliminates the product with ID "10" from the purchase list
    Then Update the purchase list to:
      | 10     | 4     |

  Scenario: Remove multiple items of the same commodity from the buy list
    Given An unidentified user with the specified purchase list:
      | 10     | 9     |
    When The user eliminates the product with ID "10" from the purchase list
    Then Update the purchase list to:
      | 10     | 8     |

  Scenario: Attempt to remove an item not present in the buy list
    Given An unidentified user with the specified purchase list:
      | 10     | 5     |
    When The user eliminates the product with ID "3" from the purchase list
    Then Raise a CommodityNotInBuyList exception