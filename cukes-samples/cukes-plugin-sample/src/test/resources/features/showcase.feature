Feature: Plugins showcase
  Scenario: fast scenario
    Given let variable "test" to be random UUID

  Scenario: slow scenario
    Given wait for 1 second

  Scenario: very slow scenario
    Given wait for 5 seconds
