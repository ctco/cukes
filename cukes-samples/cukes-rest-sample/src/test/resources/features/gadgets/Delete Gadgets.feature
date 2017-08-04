Feature: It is able to remove newly created Gadgets from the database

  Scenario: Should remove newly created Gadget
    Given request body from file "gadgets/requests/newGadget.json"
    And content type is "application/json"

    When the client performs POST request on "/gadgets"
    Then status code is 201
    And let variable "gadgetURL" equal to header "Location" value

    When the client performs DELETE request on "{(gadgetURL)}"
    Then status code is 200

    When the client performs GET request on "{(gadgetURL)}"
    Then status code is 404
    And response equals to "Object not found in the database"

  Scenario: Validation should fail if removal of Gadget is requested by invalid ID
    When the client performs DELETE request on "/gadgets/123456"
    Then status code is 404
    And response equals to "Object not found in the database"
