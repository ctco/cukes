Feature: It is able to create Gadget records and add to the the database

  Scenario: Should create another Gadget object
#   Please note that in json request body attribute
#   id, createdDate, updatedDate values should be ignored and overwritten by the backend.
    Given request body from file "gadgets/requests/newGadget.json"
    Given value assertions are case-insensitive
    And content type is "application/json"

    When the client performs POST request on "/gadgets"
    Then status code is 201
    And header "Location" contains "http://localhost:8080/gadgets/"
    And let variable "gadgetURL" equal to header "Location" value

    And should wait at most 30 seconds with interval 1 seconds until property "type" equal to "TABLET"
    When the client performs GET request on "{(header.Location)}"
    Then status code is 200
    And response contains property "id" with value other than "2000"
    And response contains property "type" with value "TABLET"
    And response contains property "name" with value "Nexus 9"
    And response contains property "owner.name" with value "Ned"
    And response contains property "owner.surname" with value "Flanders"
    And response contains property "owner.age" with value "43"
    And response contains property "createdDate" of type "long"
    And response contains property "createdDate" with value other than "1455114963103"
    And response contains an array "owner.roles" with value "evangelist"
    And response contains an array "owner.roles" with value "mall-owner"
    And response contains an array "owner.roles" of size > 1
    And response does not contain property "updatedDate"

  Scenario: Server doesn't accept content-types other than JSON
    Given request body from file "gadgets/requests/newGadget.json"
    When the client performs POST request on "/gadgets"
    Then status code is 415

  Scenario: Server doesn't support adding record with empty Gadgets type
    Given request body "{}"
    And content type is "application/json"
    When the client performs POST request on "/gadgets"
    Then status code is 400
    And response equals to "Could not add new Gadget"

  Scenario: Server doesn't support adding Book Reader Gadgets
    Given request body "{"type": "INVALID_TYPE"}"
    And content type is "application/json"
    When the client performs POST request on "/gadgets"
    Then status code is 400
