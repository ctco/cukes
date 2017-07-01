# Sample application for cukes-rabbitmq-sample

## Running

It's not possible to find publicly available RabbitMQ instance for testing therefore a sample applicaiton is available.

Just run it

```
./gradlew bootRun
```

## System under test


This application implements the following set of listeners and consumers:

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

Elements:

- Exchange (type - topic)
- 2 Listeners, both of them receive string input 
  - PrependHello - prepends 'hello' to input. E.g. 'world' is converted to 'hello, world'
  - ToUpperCase - converts input to upper case. E.g. 'world' is converted to 'WORLD'
- 4 queues
  - Q1 - for PrependHello
  - Q2 - for ToUpperCase
  - In - can be considered as entry point for consumers. They will send their input to this queue
  - Out - output queue for consumers 
  
Routing rules:

- PrependHello and ToUpperCase send response back to queue specified by 'Reply-To'
- Q1 routing key is 'prepend'
- Q2 routing key is 'upper'

Scenarios:

* Scenario 1
  * Consumer sends 'world' message to 'In' queue with routing key 'prepend', reply-to is 'Out'
  * Message reaches 'PrependHello' listener
  * After it did it's job a new message is sent to 'reply-to' queue
  * Listener for 'Out' queue receives a message
  
