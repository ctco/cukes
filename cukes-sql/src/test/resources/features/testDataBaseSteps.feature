Feature: Test DataBaseSteps

  Scenario: DB matches rows
    Then DB table Person should match:
      | id | name  | surname | age |
      | 1  | Anju  | Ujna    | 5   |
      | 2  | Sonia | Ainos   | 19  |
      | 3  | Asha  | Tear    | 35  |


  Scenario: DB contains rows
    And DB table Person should contain:
      | id | name  |
      | 1  | Anju  |
      | 2  | Sonia |
      | 3  | Asha  |

    And DB table Person should contain:
      | name  | age |
      | Anju  | 5   |
      | Sonia | 19  |

  Scenario: ContextInflater for table
    Given let variable "name" equal to "Anju"
    And  let variable "surname" equal to "Ainos"

    Then DB table Person should match:
      | id | name     | surname     | age |
      | 1  | {(name)} | Ujna        | 5   |
      | 2  | Sonia    | {(surname)} | 19  |
      | 3  | Asha     | Tear        | 35  |

    Then DB table Person should contain:
      | name     | surname     |
      | {(name)} | Ujna        |
      | Sonia    | {(surname)} |

  Scenario: DB rows count
    Then DB table Person row count should be = 3

    Then DB table Person row count should not be empty

  Scenario: DB entity is created
    When the client creates DB entities in table Person with values:
      | id | name     | surname     | age |
      | 4  | SomeName | SomeSurname | 0   |
      | 5  | Hello    | World       | 46  |
    Then DB table Person should contain:
      | id | name     | surname     | age |
      | 4  | SomeName | SomeSurname | 0   |
      | 5  | Hello    | World       | 46  |

  Scenario: DB entity is created by SQL
    When the client creates DB entities by SQL query:
    """
      INSERT INTO Person(id, name, surname, age) VALUES(4, 'SomeName', 'SomeSurname', 0);
      INSERT INTO Person(id, name, surname, age) VALUES(5, 'Hello', 'World', 46);
    """
    Then DB table Person should contain:
      | id | name     | surname     | age |
      | 4  | SomeName | SomeSurname | 0   |
      | 5  | Hello    | World       | 46  |
