Feature: LDIF showcase
  Scenario: Import LDIF and verify response against LDIF
    Given the client imports LDIF:
    """
    dn: cn=John Smith,ou=Users,dc=example,dc=com
    objectClass: inetOrgPerson
    objectClass: organizationalPerson
    objectClass: person
    objectClass: top
    cn: John Smith
    gn: John
    sn: Smith
    uid: johnsmith
    """
    When the client retrieves entity by DN "cn=John Smith,ou=Users,dc=example,dc=com"
    Then entity contains attribute "sn" with value "Smith"
    And entity contains attribute "objectclass" with 4 values
    And entity contains attribute "objectclass" with value "top"
    And entity contains attribute "objectclass" with value "person"
    And entity contains attribute "objectclass" with value "inetOrgPerson"
    And entity contains attribute "objectclass" with value "organizationalPerson"
    And entity matches LDIF:
    """
    dn: cn=John Smith,ou=Users,dc=example,dc=com
    cn: John Smith
    uid: johnsmith
    objectClass: person
    """
