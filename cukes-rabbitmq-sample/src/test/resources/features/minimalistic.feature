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
    When the client sends message with routing key "prepend"
    Then wait for message in queue "out"
    And message body contains property "body" with value "hello, world"
