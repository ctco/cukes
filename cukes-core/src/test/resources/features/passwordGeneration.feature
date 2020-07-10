Feature: Password generation - by given length

  Scenario: should generate password with provided length
    When let variable "var1" to be random password with length 8
    Then variable "var1" is set to "{(var1)}"
