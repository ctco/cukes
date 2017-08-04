# Cukes SOAP Sample

This is a sample project showing how to use **[Cukes REST extensions](../../cukes-rest)** for testing of SOAP-based web-services

## System under test

The system under test is very trivial - it exposes a calculator API that can add two integers.

The WSDL can be found under [http://localhost:8080/ws/calculator.wsdl](http://localhost:8080/ws/calculator.wsdl)

## Running tests

There is a JUnit-based test runner configured - [RunCukesSOAPTest](src/test/java/lv/ctco/cukes/soap/sample/RunCukesSOAPTest.java).

It starts calculator web-service first and then after all tests are run the web-service is stopped.

There are 2 options to run test suite:

1. Run it as JUnit test - `lv.ctco.cukes.soap.sample.RunCukesSOAPTest`
2. Run using Maven - `mvn clean test`
