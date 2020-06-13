Feature: Changing behaviour of same endpoint

  Scenario: Changing behaviour of same endpoint
    Given requesting mock for service "mockService1" and url "/ping" with method "GET"
    *     mock responds with status code "200"
    Given requesting mock for service "mockService1" and url "/ping" with method "GET"
    *     mock responds with status code "418" exactly 1 time
    Given requesting mock for service "mockService1" and url "/ping" with method "GET"
    *     mock responds with status code "429" exactly 1 time

    When the client performs GET request on "/ping"
    Then status code is 418
    When the client performs GET request on "/ping"
    Then status code is 429
    When the client performs GET request on "/ping"
    Then status code is 200
