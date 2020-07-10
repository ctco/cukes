Feature: Multipart upload showcase

  Scenario: Multipart upload
    Given request body is a multipart file "multipart/test.txt"
    When the client performs POST request on "/multipart"
    Then status code is 200
    And response contains "Hello, world"
