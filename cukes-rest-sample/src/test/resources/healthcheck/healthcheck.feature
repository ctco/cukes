Feature: Server is healthy

  Scenario: Should return pong response on /ping
    When the client performs GET request on /ping
    Then status code is 200
    And response equals to "pong"
