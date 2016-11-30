Feature: Server is healthy

  Background:
    Given baseUri is "http://localhost:8888/"

  Scenario: Should return pong response on /healthcheck
    When the client performs GET request on "/healthcheck"
    Then status code is 200
    And let variable "var" equal to header "Content-Length" value

  Scenario: Scenario is isolated from another
    When the client performs GET request on "/healthcheck"
    Then status code is 200
    Given request body "Hello. I'm Isolated!"
    Given queryParam "param" is "isolated"

  Scenario: GET is performed without extra request body
    When the client performs GET request on "/healthcheck"
    Then status code is 200
    And let variable "{(isolated)}" equal to "not_isolated"

  Scenario: Float values should be compared correctly
    Given baseUri is "http://localhost:8080/"
    When the client performs GET request on "/staticTypes"
    Then status code is 200
    Then response contains property "prop[0].float" with value "26.505515"
    Then response contains property "prop[0].string" with value "{(isolated)}"

  Scenario: Auto-cached variables are cleaned after each request
    Given baseUri is "http://localhost:8080/"
    When the client performs GET request on "/customHeaders"
    Then status code is 200

    When the client performs GET request on "/staticTypes"
    Then status code is 200
    # {(header.Custom-Header)} should be empty
    Then response contains property "prop[0].long" not matching pattern "{(header.Custom-Header)}"
