# Cukes LDAP Sample

This is a sample project showing how to use **[Cukes LDAP extensions](../../cukes-ldap)**

## Overall design

For simplicity and to avoid any external dependencies the test suite runs own instance of in-memory LDAP server with predefined schema.
This avoids need of hosting it externally and brings more autonomy.

[Apache DS](http://directory.apache.org/apacheds/) is used as in-memory LDAP server.

If you are interested how it works you can check **[lv.ctco.cukes.ldap.sample.EmbeddedLDAPServer](src/main/java/lv/ctco/cukes/ldap/sample/EmbeddedLDAPServer.java)**.

**[cukes.properties](src/test/resources/cukes.properties)** is configured in a way to connect to in-memory LDAP server.

## System under test

The system under test is very simple - it does nothing except for providing some popular LDAP schemas.

## Running tests

There is a JUnit-based test runner configured - [lv.ctco.cukes.ldap.sample.RunCukesLDAPTest](src/test/java/lv/ctco/cukes/ldap/sample/RunCukesLDAPTest.java).

It starts in-memory LDAP server first, then runs all tests and in the end shuts down LDAP server.

There are 2 options to run test suite:

1. Run it as JUnit test - `lv.ctco.cukes.ldap.sample.RunCukesLDAPTest`
2. Run using Maven - `mvn clean test`
