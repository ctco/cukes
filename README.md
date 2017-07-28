[![Stories in Ready](https://img.shields.io/waffle/label/ctco/cukes/ready.svg?label=Ready&style=flat)](https://waffle.io/ctco/cukes)
[![Join the chat at https://gitter.im/ctco/cukes](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/ctco/cukes?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![wercker status](https://app.wercker.com/status/91bd08250ec1cee694c8d0e5c95f85ce/s/master "wercker status")](https://app.wercker.com/project/byKey/91bd08250ec1cee694c8d0e5c95f85ce)
[![Maven](https://img.shields.io/maven-central/v/lv.ctco.cukes/cukes.svg)](http://search.maven.org/#search|ga|1|lv.ctco.cukes)

# ![cukes-rest logo](assets/cukes-rest-logo.png)
**cukes-rest** takes simplicity of **Cucumber** and provides bindings for HTTP specification. As a sugar on top, **cukes-rest**
adds steps for storing and using request/response content from a file system, variable support in .features, context 
inflation in all steps and a custom plug-in system to allow users to add additional project specific
content. 

## Resources
- [Wiki](https://github.com/ctco/cukes/wiki)
- [Samples](https://github.com/ctco/cukes/wiki/Test-Samples)
- [Open Issues](https://github.com/ctco/cukes/issues)
- [Waffle](https://waffle.io/ctco/cukes)

## Sample Test

```gherkin
Feature: Gadgets are great!

  Background:
    Given baseUri is http://my-server.com/rest/

  Scenario: Should create another Gadget object
    Given request body from file gadgets/requests/newGadget.json
    And content type is "application/json"

    When the client performs POST request on /gadgets
    Then status code is 201
    And header Location contains "http://localhost:8080/gadgets/"

    When the client performs GET request on {(header.Location)}
    Then status code is 200
    And response contains property "id" with value other than "2000"
    And response contains property "name" with value "Nexus 9"
    And response does not contain property "updatedDate"
```

There are three sections available to be used in a Feature files:
- Feature - a description of a feature under test
- Background - set of steps to be executed before every scenario (usually these are preconditions)
- Scenario - a single automated test case

As well as three groups of steps available
- Given - building up a HTTP request to be performed
- When - executing the request
- Then - assertions based on a response received
         
[More information can be found in the presentation right here!](https://speakerdeck.com/larchaon/getting-started-with-cukes-rest)
          
## Prerequisites
- JDK 1.6+

## Dependency
The dependencies are stored in [Maven Central](http://search.maven.org/#search|ga|1|lv.ctco.cukes)

### cukes-rest: core dependency with all you need to get started with the framework (Maven)

```xml
<dependency>
    <groupId>lv.ctco.cukes</groupId>
    <artifactId>cukes-rest</artifactId>
    <version>${cukes-rest.version}</version>
</dependency>
```

### cukes-rest-loadrunner: experimental plugin for HP LoadRunner script generation out of cukes-rest test case

```xml
<dependency>
    <groupId>lv.ctco.cukes</groupId>
    <artifactId>cukes-rest-loadrunner</artifactId>
    <version>${cukes-rest.version}</version>
</dependency>
```

## Getting Started

There are two options to start local server with Sample Application:

1. Run **SampleApplicaiton.java** with following params `server server.yml` from **$MODULE_DIR$**
2. Execute Package/Install Maven phase of the parent project **cukes-rest-all**

### Running tests

*Precondition*: in order for all tests to pass successfully, please make sure you started fresh instance of Sample Application.

- To start a specific Feature/Scenario, either change **CucumberOption** in **RunCukesTest.java** or run Feature file directly from you IDE
- To start all tests run **RunCukesTest.java** from sub-project **cukes-rest-sample**
- To start all tests right from Maven, execute _test_ phase in project **cukes-rest-sample**
