# Cukes RabbitMQ

**cukes-rabbitmq** is an extension to **cukes** framework that brings capabilities to test RabbitMQ-based systems.


## Resources

- [RabbitMQ](https://www.rabbitmq.com/)
- [AMQP](https://www.amqp.org/)

## Sample test

Here is a simple test:

```gherkin
Feature: Minimalistic configuration
  Scenario: Should prepend 'hello' to message
    Given prepare new message
    And message body:
    """
    {
      "body": "world"
    }
    """
    And reply-to is "out"
    And bind queue "out" with routing key "out"
    When the client sends message with routing key "say-hello"
    Then wait for message in queue "out"
    And message body contains property "body" with value "hello, world"
```

This test does the following:

1. Prepares a JSON-based message
2. Message is sent to AMQP exchange with routing key *'say-hello'*
3. Test expects that the response will be read from output queue *'out'*

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
    <artifactId>cukes-rabbitmq</artifactId>
    <version>${cukes.version}</version>
    <scope>test</scope>
</dependency>
```

### Gradle dependency

Add the following dependency to *build.gradle*:

```
testCompile "lv.ctco.cukes:cukes-rabbitmq:${cukes.version}"
```

### cukes.properties

Here is an example for configuration (to be placed in *cukes.properties* file):

```
# Connection parameters
cukes.rabbitmq.host=localhost
cukes.rabbitmq.port=5672
cukes.rabbitmq.user=guest
cukes.rabbitmq.password=guest
cukes.rabbitmq.vhost=default

# Default content type for messages
cukes.rabbitmq.content-type=application/json

# Default waiting timeout
cukes.rabbitmq.read-timeout.default=1

# Predefine exchange
cukes.rabbitmq.exchange.0.name=exchange
cukes.rabbitmq.exchange.0.type=topic

# Use exchange by default
cukes.rabbitmq.exchange.default=exchange
```

**Note** that only first section related to connection parameters is required.

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
public class RunCukesRabbitMQTest {

}

```

## Configuration

Here is list of configuration properties that are available:

| Property           | Meaning                                   | Default value        |
|--------------------|-------------------------------------------|----------------------|
|cukes.rabbitmq.host | Host where RabbitMQ instance is running | localhost |
|cukes.rabbitmq.port | Port which RabbitMQ instance listens on | 5672 |
|cukes.rabbitmq.user | Username to connect to RabbitMQ | guest |
|cukes.rabbitmq.password | Password for the user | guest |
|cukes.rabbitmq.vhost | Virtual host | default |
|cukes.rabbitmq.content-type | Default content type for messages | |
|cukes.rabbitmq.read-timeout.default | Default read timeout in seconds to wait for response in queues | 5 |
|cukes.rabbitmq.exchange.0.name | Name of first declared exchange | |
|cukes.rabbitmq.exchange.0.type | Type of first declared exchange | |
|cukes.rabbitmq.exchange.1.name | Name of second declared exchange | |
|cukes.rabbitmq.exchange.1.type | Type of second declared exchange | |
|cukes.rabbitmq.exchange.X.name | Name of another declared exchange | |
|cukes.rabbitmq.exchange.X.type | Type of another declared exchange | |
|cukes.rabbitmq.exchange.default | Name of default exchange that is used | |

**Note** It is possible to declare as many exchanges as you want.

## More examples

For more examples please refer to **[cukes-rabbitmq-sample](../cukes-rabbitmq-sample)** module.
