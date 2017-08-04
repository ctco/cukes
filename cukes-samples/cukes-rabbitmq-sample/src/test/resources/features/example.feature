Feature: DSL showcase
  Background:
    Given connecting to host "localhost"
    And server responds on port 5672
    And connecting using username "guest" and password "guest"
    And virtual host is "default"
    And not using SSL
    And declare exchange "exchange" of type "topic"
#    And use exchange "exchange" by default

  Scenario: Should convert message to upper case
    Given prepare new message
    And content-type is "text/plain"
    And message body is "hello"
    And reply-to is "out"
    And bind queue "out" to exchange "exchange" with routing key "out"
    When the client sends message to exchange "exchange" with routing key "upper"
    Then wait for message in queue "out" for not more than 5 seconds
    # Double quotes here are just because String to JSON conversion during payload processing
    And message body equals to ""HELLO""

  Scenario: Should prepend 'hello' to message
    Given use exchange "exchange" by default
    And prepare new message
    And message body:
    """
    {
      "body": "world"
    }
    """
    And content-type is "application/json"
    And reply-to is "out"
    And bind queue "out" with routing key "out"
    When the client sends message with routing key "prepend"
    Then wait for message in queue "out" for not more than 5 seconds
    And message body contains property "body" with value "hello, world"
