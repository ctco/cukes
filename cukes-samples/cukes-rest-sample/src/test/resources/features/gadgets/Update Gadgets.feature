Feature: It is able to update newly Created Gadget

  Background:
    Given request body from file "gadgets/requests/newGadget.json"
    And content type is "application/json"

    When the client performs POST request on "/gadgets"
    Then status code is 201
    And let variable "gadgetURL" equal to header "Location" value

    When the client performs GET request on "{(gadgetURL)}"
    Then status code is 200
    And response contains property "id" with value other than "2000"
    And let variable "id" equal to property "id" value
    And let variable "created" equal to property "createdDate" value

  Scenario: Should update existing Gadget object
#   Please note that in json request body attribute
#   id, createdDate, updatedDate values should be ignored and overwritten by the backend.
    Given request body from file "gadgets/requests/updatedGadget.json"
    And content type is "application/json"
    When the client performs PUT request on "{(gadgetURL)}"
    Then status code is 200

    When the client performs GET request on "{(gadgetURL)}"
    Then status code is 200
    And response contains property "id" with value other than "3000"
    And response contains property "type" with value "LAPTOP"
    And response contains property "name" with value "Acer Aspire R1"
    And response contains property "owner.name" with value "Mr."
    And response contains property "owner.surname" with value "Burns"
    And response contains property "owner.age" with value "65"
    And response contains property "createdDate" of type "long"
    And response contains property "createdDate" with value "{(created)}"
    And response contains property "updatedDate" of type "long"
    And response contains property "updatedDate" with value other than "1456326973103"

  Scenario: Server cannot update non-existing Gadget ID
    Given request body from file "gadgets/requests/updatedGadget.json"
    And content type is "application/json"
    When the client performs PUT request on "/gadgets/123456"
    Then status code is 400
    And response equals to "Could not update Gadget with ID: 123456"

  Scenario: Server doesn't accept for update content-types other than JSON
    Given request body from file "gadgets/requests/updatedGadget.json"
    When the client performs PUT request on "{(gadgetURL)}"
    Then status code is 415

  Scenario: Server doesn't support updating record to the one with empty Gadget type
    Given request body "{}"
    And content type is "application/json"
    When the client performs PUT request on "{(gadgetURL)}"
    Then status code is 400
    And response equals to "Could not update Gadget with ID: {(id)}"

  Scenario: Server doesn't support updating to Book Reader Gadgets
    Given request body "{"type": "INVALID_TYPE"}"
    And content type is "application/json"
    When the client performs PUT request on "{(gadgetURL)}"
    Then status code is 400
