# Cukes OAuth Sample

This is a sample project showing how to use **[Cukes OAuth extensions](../../cukes-oauth)**

## Overall design

For simplicity and to avoid any external dependencies the test suite runs own instance of in-memory OAuth server and a resource provider with a couple of secured endpoints.
This avoids need of hosting it externally and brings more autonomy.

**[cukes.properties](src/test/resources/cukes.properties)** is configured in a way to connect to use in-memory OAuth server.

## System under test

The system under test is very simple - only a couple of OAuth secured endpoints.

## Running tests

There is a JUnit-based test runner configured - [lv.ctco.cukes.oauth.sample.RunCukesOAuthTest](src/test/java/lv/ctco/cukes/oauth/sample/RunCukesOAuthTest.java).

It starts in-memory OAuth server and resource provider first, then runs all tests and in the end shuts down OAuth server and resource provider.

There are 2 options to run test suite:

1. Run it as JUnit test - `lv.ctco.cukes.oauth.sample.RunCukesOAuthTest`
2. Run using Maven - `mvn clean test`
