Feature: Create entity showcase
  Scenario: Should create entity from LDIF inline
    When the client creates entity using LDIF:
    """
    dn: cn=inline,ou=Users,dc=example,dc=com
    objectClass: inetOrgPerson
    objectClass: organizationalPerson
    objectClass: person
    objectClass: top
    gn: test
    sn: test
    cn: inline
    """
    And the client retrieves entity by DN "cn=inline,ou=Users,dc=example,dc=com"
    Then entity exists

  Scenario: Should create entity from LDIF file
    When the client creates entity using LDIF from file "ldif/test.ldif"
    And the client retrieves entity by DN "cn=from-file,ou=Users,dc=example,dc=com"
    Then entity exists
