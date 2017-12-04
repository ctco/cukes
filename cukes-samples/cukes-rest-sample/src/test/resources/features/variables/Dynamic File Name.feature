Feature: It is able to use variable in file names
  Background:
    Given let variable "fileName" equal to "newGadget.json"

  Scenario: Should remove newly created Gadget
    Given request body from file "variables/requests/{(fileName)}"
    And content type is "application/json"

    When the client performs POST request on "/gadgets"
    Then status code is 201
    And let variable "gadgetURL" equal to header "Location" value

    When the client performs DELETE request on "{(gadgetURL)}"
    Then status code is 200
