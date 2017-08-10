Feature: Issue 92
  Scenario: should generate proper report
    Given let variable "var1" to be random UUID
    And let variable "var2" to be random UUID
    Then variable "var1" is set to "{(var1)}"
    Then variable "var2" is set to "{(var2)}"
    Then variable "var1" is set to "{(var1)}"
