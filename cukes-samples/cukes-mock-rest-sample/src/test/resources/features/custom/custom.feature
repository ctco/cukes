Feature: Custom integration

  Scenario: Simple example
    Given requesting mock for service "mockService1" and url "/ping" with method "GET"
    *     mock responds with status code "200"

    When the client performs GET request on "/ping"
    Then status code is 200

  Scenario: More complex example
    ##### Prepare matching request
    Given requesting mock for service "mockService1" and url "/api/ping" with method "GET"
    * with header "MockedHeader" with value "HeaderValue"
    ##### Prepare mock response matcing request
    *     mock response will have header "expected" with value "hello"
    *     mock response will have body
    """
    pong
    """
    *     mock responds with status code "200"

    # Test
    Given header MockedHeader with value "HeaderValue"
    When the client performs GET request on "/api/ping"
    Then status code is 200
    And  response equals to "pong"
    And  header "expected" contains "hello"
