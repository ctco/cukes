Feature: Custom integration

  Background:
    Given baseUri is "http://localhost:8888/"

  Scenario: Should persist state within scenario
    And save state for "healthcheck" as STARTED
    When the client performs GET request on "/healthcheck"
    And save state for "healthcheck" as WORKING
    Then status code is 200
    And save state for "healthcheck" as STOPPED
    And state for "healthcheck" should be STOPPED

  Scenario: Should not have have old state
    Then state should be missing for "healthcheck"
