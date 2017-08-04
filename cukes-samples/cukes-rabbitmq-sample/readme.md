# Cukes RabbitMQ Sample

This is a sample project showing how to use **[Cukes RabbitMQ extensions](../../cukes-rabbitmq)**

## Overall design

To avoid coupling with external providers sample project runs own AMQP broker. 

[Apache Qpid](https://qpid.apache.org/) is used as in-memory AMQP broker.

If you would like how it works you can start with [lv.ctco.cukes.rabbitmq.sample.configuration.InMemoryAMQPBroker](src/main/java/lv/ctco/cukes/rabbitmq/sample/configuration/InMemoryAMQPBroker.java).

**[cukes.properties](src/test/resources/cukes.properties)** is configured to use in-memory AMQP broker.

## System under test

System under test can be described with the following diagram:

```

                                                +-----------------+
+-+----+-+                        +-+----+-+    |                 |
| | In | +--------+    +--------->| | Q1 | +--->|   PrependHello  |
+-+----+-+        v    |          +-+----+-+    |                 |
               +-------+------+                 +-----------------+
               |              |
               |   Exchange   |
               |              |
               +---+---+------+                 +-----------------+
+-+----+--+        |   |          +-+----+-+    |                 |
| | Out|  |<-------+   +--------->| | Q2 | +--->|   ToUpperCase   |
+-+----+--+                       +-+----+-+    |                 |
                                                +-----------------+

```

It declares one exchange, two workers (*PrependHello* and *ToUpperCase*) and a couple of queues (*in* and *out*).

Clients will send in input messages to the exchange and based on routing key they will be consumed by one of the workers.

Response message will be send to *out* queue.

## Running tests

There is a JUnit-based test runner configured - [lv.ctco.cukes.rabbitmq.sample.RunCukesRabbmitMQTest](src/test/java/lv/ctco/cukes/rabbitmq/RunCukesRabbmitMQTest.java).

It starts in-memory AMQP broker first, then runs all tests and in the end shuts down AMQP broker.

There are 2 options to run test suite:

1. Run it as JUnit test - `lv.ctco.cukes.rabbitmq.sample.RunCukesRabbmitMQTest`
2. Run using Maven - `mvn clean test`

