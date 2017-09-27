Feature: Search entities showcase
  Scenario: Should search by filter expression
    When the client searches entities within DN "ou=system" by filter "(uid=admin)"
    Then search result has size 1
    And take entity with index 1 from search results
    And entity contains attribute "uid" with value "admin"
    And entity contains attribute "uid"
    And entity contains attribute "displayname" with value "Directory Superuser"
