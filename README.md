[![Stories in Ready](https://badge.waffle.io/ctco/cukes-rest.png?label=ready&title=Ready)](https://waffle.io/ctco/cukes-rest)[![Join the chat at https://gitter.im/ctco/cukes-rest](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/ctco/cukes-rest?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)[![Build Status](https://snap-ci.com/ctco/cukes-rest/branch/master/build_image)](https://snap-ci.com/ctco/cukes-rest/branch/master)
# ![cukes-rest logo](cukes-rest-logo.png)
**cukes-rest** takes simplicity of **Cucumber** and provides bindings for HTTP specification. As a sugar on top, **cukes-rest**
adds steps for storing and using request/response content from a file system, variable support in .features, context 
inflation in all steps and a custom plug-in system to allow users to add additional project specific
content. 

```gherkin
Feature: Server is healthy

  Scenario: Should return pong response on /ping
    When the client performs GET request on /ping
    Then status code is 200
    And response equals to "pong"
```
          
## Prerequisites
- JDK 1.6+

## Dependency

### cukes-rest: core dependency with all you need to get started with the framework

```xml
<dependency>
    <groupId>lv.ctco.cukesrest</groupId>
    <artifactId>cukes-rest</artifactId>
    <version>${cukes-rest.version}</version>
</dependency>
```

### cukes-rest-loadrunner: experimental plugin for HP LoadRunner script generation out of cukes-rest test case

```xml
<dependency>
    <groupId>lv.ctco.cukesrest</groupId>
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

Hint: Please use the Jayway JSONPath viewer to construct needed JSONPath queries: http://jsonpath.herokuapp.com/
