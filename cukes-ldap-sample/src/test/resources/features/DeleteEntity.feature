Feature: Delete entity showcase
  Scenario: Should delete entity by DN
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
    """
    When the client retrieves entity by DN "cn={(cn)},ou=Users,dc=example,dc=com"
    Then entity exists
    And entity matches LDIF:
    """
    dn: cn={(cn)},ou=Users,dc=example,dc=com
    cn: {(cn)}
    """
    When the client deletes entity with DN "cn={(cn)},ou=Users,dc=example,dc=com"
    And the client retrieves entity by DN "cn={(cn)},ou=Users,dc=example,dc=com"
    Then entity does not exist

  Scenario: Should delete subtree
    Given let variable "group" to be random UUID
    Given let variable "cn" to be random UUID
    And the client imports LDIF:
    """
    dn: ou={(group)},dc=example,dc=com
    objectClass: organizationalUnit
    objectClass: top
    ou: {(group)}

    dn: cn={(cn)},ou={(group)},dc=example,dc=com
    objectClass: inetOrgPerson
    objectClass: organizationalPerson
    objectClass: person
    objectClass: top
    gn: test
    sn: test
    cn: {(cn)}
    """
    When the client retrieves entity by DN "ou={(group)},dc=example,dc=com"
    Then entity exists
    When the client deletes entity with DN "ou={(group)},dc=example,dc=com"
    And the client retrieves entity by DN "ou={(group)},dc=example,dc=com"
    Then entity does not exist
