Feature: It is able to retrieve Gadget records stored in the database

  Scenario: Should retrieve all available Gadgets in an Array
    When the client performs GET request on /gadgets
    Then status code is 200
    And response contains an array "gadgets" of size ">=5"

  Scenario: Check attributes of a single Gadget record with ID - 1858 (search)
    When the client performs GET request on /gadgets
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
    When the client performs GET request on /gadgets/1860
    Then status code is 200
    And response contains property "id" with value "1860"
    And response contains property "type" with value "SMART_WATCH"
    And response contains property "name" with value "Apple Watch"
    And response contains property "owner.name" with value "Marge"
    And response contains property "owner.surname" with value "Simpson"
    And response contains property "owner.age" with value "36"
    And response contains property "createdDate" of type "long"
    And response does not contain property "updatedDate"

  Scenario: Checks the validation message if requesting invalid Gadget ID
    When the client performs GET request on /gadgets/123456
    Then status code is 404
    And response equals to "Object not found in the database"
