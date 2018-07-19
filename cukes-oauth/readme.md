# Cukes OAuth

**cukes-oauth** is an extension to **cukes** framework that brings capabilities to test OAuth-secured endpoints

## Sample test

Here is a simple test:

```gherkin
Feature: Call OAuth-protected resources
  Scenario: Read with allowed scopes
    When using OAuth
    Then the client performs GET request on "/read"
    And status code is 200
    And response contains "Read success"
```

OAuth can be combined with both [cukes-rest](../cukes-rest) and [cukes-graphql](../cukes-graphql).

##

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
    <artifactId>cukes-oauth</artifactId>
    <version>${cukes.version}</version>
    <scope>test</scope>
</dependency>
```

### Gradle dependency

Add the following dependency to *build.gradle*:

```
testCompile "lv.ctco.cukes:cukes-oauth:${cukes.version}"
```

### cukes.properties

Here is an example for configuration (to be placed in *cukes.properties* file):

```
cukes.oauth.enabled = true
cukes.oauth.client_id = clientid
cukes.oauth.client_secret = secret
cukes.oauth.grant_type = client_credentials
cukes.oauth.auth_server = http://localhost:8080/oauth/token
cukes.oauth.scope = read write
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
public class RunCukesOAuthTest {

}

```

## Configuration

Here is list of configuration properties that are available:

| Property           | Meaning                                   | Default value        |
|--------------------|-------------------------------------------|----------------------|
| oauth.enabled | If OAuth security is enabled | false |
| oauth.client_id | OAuth client ID | |
| oauth.client_secret | OAuth client secret | |
| oauth.grant_type | Grant type. Can be either `password` or `client_credentials` | |
| oauth.auth_server | URL of OAuth authorization server to get access token from | |
| oauth.scope | List of scopes provided for access token retrieval | |

## More examples

For more examples please refer to **[cukes-oauth-sample](../cukes-oauth-sample)** module.
