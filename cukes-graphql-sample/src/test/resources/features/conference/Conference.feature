Feature: GraphQL Conference

  Scenario: Should retrieve general information about the event
    Given query from file "queries/Berlin2017Conference.graphql"
    When the query is executed
    Then response contains property "conference.name" with value "GraphQL-Europe"
    And response contains property "conference.dateStart" with value "2017-05-21"
    And response contains an array "conference.speakers" of size > 10
    And response contains an array "conference.sponsors" with object having property "name" with value "Facebook"

  Scenario: Should retrieve ticket options
    Given query:
    """
    {
      conference(edition: Berlin2017) {
        tickets {
          name
        }
      }
    }
    """
    When the query is executed
    And response contains an array "conference.tickets" of size 3
    And response contains an array "conference.tickets" with object having property "name" with value "Early Bird"
    And response contains an array "conference.tickets" with object having property "name" with value "Regular"
    And response contains an array "conference.tickets" with object having property "name" with value "Late Bird"
