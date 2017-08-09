Feature: Modify entity showcase
  Scenario: Should replace attributes
    Given let variable "cn" to be random UUID
    And the client imports LDIF:
    """
    dn: cn={(cn)},ou=Users,dc=example,dc=com
    objectClass: inetOrgPerson
    objectClass: organizationalPerson
    objectClass: person
    objectClass: top
    gn: test
    sn: test
    cn: {(cn)}
    telephoneNumber: 12345
    """
    And prepare new entity modification
    And change attribute "telephoneNumber" replace value "67890"
    When the client updates entity with DN "cn={(cn)},ou=Users,dc=example,dc=com" using prepared modifications
    And the client retrieves entity by DN "cn={(cn)},ou=Users,dc=example,dc=com"
    Then entity exists
    And entity contains attribute "telephoneNumber" with 1 value
    And entity contains attribute "telephoneNumber" with value "67890"

  Scenario: Should add attributes
    Given let variable "cn" to be random UUID
    And the client imports LDIF:
    """
    dn: cn={(cn)},ou=Users,dc=example,dc=com
    objectClass: top
    objectClass: organizationalPerson
    objectClass: inetOrgPerson
    objectClass: person
    gn: test
    sn: test
    cn: {(cn)}
    """
    And prepare new entity modification
    And change attribute "telephoneNumber " add value "12345"
    And change attribute "telephoneNumber " add value "67890"
    When the client updates entity with DN "cn={(cn)},ou=Users,dc=example,dc=com" using prepared modifications
    And the client retrieves entity by DN "cn={(cn)},ou=Users,dc=example,dc=com"
    Then entity exists
    And entity contains attribute "telephoneNumber" with 2 values
    And entity contains attribute "telephoneNumber" with value "12345"
    And entity contains attribute "telephoneNumber" with value "67890"

  Scenario: Should remove attribute
    Given let variable "cn" to be random UUID
    And the client imports LDIF:
    """
    dn: cn={(cn)},ou=Users,dc=example,dc=com
    objectClass: top
    objectClass: organizationalPerson
    objectClass: inetOrgPerson
    objectClass: person
    gn: test
    sn: test
    cn: {(cn)}
    telephoneNumber: 12345
    telephoneNumber: 67890
    """
    And prepare new entity modification
    And change attribute "telephoneNumber " remove value "12345"
    When the client updates entity with DN "cn={(cn)},ou=Users,dc=example,dc=com" using prepared modifications
    And the client retrieves entity by DN "cn={(cn)},ou=Users,dc=example,dc=com"
    Then entity exists
    And entity contains attribute "telephoneNumber" with 1 value
    And entity contains attribute "telephoneNumber" with value "67890"
