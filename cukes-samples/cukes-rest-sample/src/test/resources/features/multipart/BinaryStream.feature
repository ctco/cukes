Feature: Binary upload showcase

  Scenario: Excel Octet-Stream upload
    Given request body from binary file "multipart/testExcel.xlsx"
    Given header Content-Type with value "application/octet-stream"
    When the client performs POST request on "/binary"
    Then status code is 200
    And response contains "6180"
