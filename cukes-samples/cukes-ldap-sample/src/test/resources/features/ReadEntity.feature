Feature: Read entity showcase
  Scenario: Read admin user
    When the client retrieves entity by DN "uid=admin,ou=system"
    Then entity contains attribute "uid" with value "admin"
    And entity contains attribute "uid"
    And entity contains attribute "displayname" with value "Directory Superuser"
