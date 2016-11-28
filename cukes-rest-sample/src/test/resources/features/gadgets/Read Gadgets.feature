Feature: It is able to retrieve Gadget records stored in the database

  Scenario: Should retrieve all available Gadgets in an Array
    When the client performs GET request on "/gadgets"
    Then status code is 200
    And response contains an array "gadgets" of size >= 5

  Scenario: Check attributes of a single Gadget record with ID - 1858 (search)
    When the client performs GET request on "/gadgets"
    Then status code is 200
    And response contains property "gadgets.find{gadget->gadget.id==1858}.id" with value "1858"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.type" with value "LAPTOP"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.name" with value "Macbook Air"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.owner.name" with value "Homer"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.owner.surname" with value "Simpson"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.owner.age" with value "38"
    And response contains property "gadgets.find{gadget->gadget.id==1858}.createdDate" of type "long"
    And response does not contain property "gadgets.find{gadget->gadget.id==1858}.updatedDate"

  Scenario: Check attributes of a single Gadget record (request by ID)
    When the client performs GET request on "/gadgets/1860"
    Then status code is 200
    And response contains property "id" with value "1860"
    And response contains property "type" with value "SMART_WATCH"
    And response contains property "name" with value "Apple Watch"
    And response contains property "owner.name" with value "Marge"
    And response contains property "owner.surname" with value "Simpson"
    And response contains property "owner.age" with value "36"
    And response contains property "createdDate" of type "long"
    And response does not contain property "updatedDate"

  Scenario Outline: Checks the validation message if requesting invalid Gadget ID
    When the client performs GET request on "/gadgets/<ID>"
    Then status code is 404
    And response equals to "Object not found in the database"
    Examples:
      | ID     |
      | 123456 |
      | 654321 |

  Scenario: Should get wrong expectation
    And should wait at most 30 seconds with interval 1 seconds until property "type" equal to "SMART_WATCH"
    When the client performs GET request on "/gadgets/1860"
    Then a failure is expected
    And should wait at most 30 seconds with interval 1 seconds until property "type" equal to "NO_SUCH_TYPE" or fail with "SMART_WATCH"
    When the client performs GET request on "/gadgets/1860"
    And it fails with "CucumberException"

  Scenario: Should fetch only one gadget with top param
    Given queryParam "$top" is "1"
    When the client performs GET request on "/gadgets"
    Then status code is 200
    And response contains an array "gadgets" of size 1

#  TODO: Remove first HTTP call once issue with scopes is fixed
  Scenario: Should fetch only one gadget with top param url encoded
    Given let variable "url_encoding_enabled" equal to "false"
    When the client performs GET request on "/gadgets"
    Then status code is 200
    When the client performs GET request on "/gadgets?%24top=1"
    Then status code is 200
    And response contains an array "gadgets" of size 1
