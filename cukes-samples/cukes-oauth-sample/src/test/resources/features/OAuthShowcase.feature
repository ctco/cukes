Feature: Call OAuth-protected resources

  Scenario: Read with allowed scopes
    When using OAuth
    Then the client performs GET request on "/read"
    And status code is 200
    And response contains "Read success"

  Scenario: Write with allowed scopes
    When using OAuth
    Then the client performs GET request on "/write"
    And status code is 200
    And response contains "Write success"

  Scenario: Call resource with less scopes
    When using OAuth
    And using "read" scopes
    Then the client performs GET request on "/write"
    And status code is 403
