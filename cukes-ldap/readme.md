# Cukes LDAP

**cukes-ldap** is an extension to **cukes** framework that brings capabilities to manipulate data stored in LDAP-based storages.

Typical use case include (but not limited to):

- Using LDAP as simple data storage (similar to RDBMS)
- Reading data from Active Directory

## Resources

- [LDAP](https://en.wikipedia.org/wiki/Lightweight_Directory_Access_Protocol)

## Sample test

Here is a simple test:

```gherkin
Feature: Read entity showcase
  Scenario: Read admin user
    When the client retrieves entity by DN "uid=admin,ou=system"
    Then entity contains attribute "uid" with value "admin"
    And entity contains attribute "displayname" with value "Directory Superuser"
```

It does not do much, however it shows the basic syntax - perform LDAP lookup and verify retrieved results.

## Prerequisites

- Java-based project
- Java 1.8
- Maven or Gradle as build system

## Using in project

### Maven dependency

Add the following dependency to *pom.xml*:

```xml
<dependency>
    <groupId>lv.ctco.cukes</groupId>
    <artifactId>cukes-ldap</artifactId>
    <version>${cukes.version}</version>
    <scope>test</scope>
</dependency>
```

### Gradle dependency

Add the following dependency to *build.gradle*:

```
testCompile "lv.ctco.cukes:cukes-ldap:${cukes.version}"
```

### cukes.properties

Here is an example for configuration (to be placed in *cukes.properties* file):

```
cukes.ldap.url: ldap://localhost:10389
cukes.ldap.user: uid=admin,ou=system
cukes.ldap.password: secret
```

### Putting all together

To enable running tests with *JUnit* place the following code under *src/test/java* folder:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCukesLDAPTest {

}

```

## Configuration

Here is list of configuration properties that are available:

| Property           | Meaning                                   | Default value        |
|--------------------|-------------------------------------------|----------------------|
| cukes.ldap.url     | ULR for LDAP server                       | ldap://localhost:389 |
| cukes.ldap.user    | User name that is used to connect to LDAP | cn=admin             |
| cukes.ldap.password| User's password                           | password             |

## More examples

For more examples please refer to **[cukes-ldap-sample](../cukes-ldap-sample)** module.
