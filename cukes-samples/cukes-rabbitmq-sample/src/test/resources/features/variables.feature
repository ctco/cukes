Feature: Variables usage showcase
  Scenario: Should prepend 'hello' to message
    Given let variable "name" to be random UUID
    And prepare new message
    And message body:
    """
    {
      "body": "{(name)}"
    }
    """
    And reply-to is "out"
    And bind queue "out" with routing key "out"
    When the client sends message with routing key "prepend"
    Then wait for message in queue "out" for not more than 5 seconds
    And message body contains property "body" with value "hello, {(name)}"
